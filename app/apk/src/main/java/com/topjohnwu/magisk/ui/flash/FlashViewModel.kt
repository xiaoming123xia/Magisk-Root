package com.mobai.magisk.ui.flash

import android.os.Build
import android.view.MenuItem
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.mobai.magisk.BR
import com.mobai.magisk.R
import com.mobai.magisk.arch.BaseViewModel
import com.mobai.magisk.core.Const
import com.mobai.magisk.core.Info
import com.mobai.magisk.core.ktx.reboot
import com.mobai.magisk.core.ktx.synchronized
import com.mobai.magisk.core.ktx.timeFormatStandard
import com.mobai.magisk.core.ktx.toTime
import com.mobai.magisk.core.tasks.FlashZip
import com.mobai.magisk.core.tasks.MagiskInstaller
import com.mobai.magisk.core.utils.MediaStoreUtils
import com.mobai.magisk.core.utils.MediaStoreUtils.outputStream
import com.mobai.magisk.databinding.set
import com.mobai.magisk.events.SnackbarEvent
import com.topjohnwu.superuser.CallbackList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FlashViewModel : BaseViewModel() {

    enum class State {
        FLASHING, SUCCESS, FAILED
    }

    private val _state = MutableLiveData(State.FLASHING)
    val state: LiveData<State> get() = _state
    val flashing = state.map { it == State.FLASHING }

    @get:Bindable
    var showReboot = Info.isRooted
        set(value) = set(value, field, { field = it }, BR.showReboot)

    val items = ObservableArrayList<ConsoleItem>()
    lateinit var args: FlashFragmentArgs

    private val logItems = mutableListOf<String>().synchronized()
    private val outItems = object : CallbackList<String>() {
        override fun onAddElement(e: String?) {
            e ?: return
            items.add(ConsoleItem(e))
            logItems.add(e)
        }
    }

    fun startFlashing() {
        val (action, uri) = args

        viewModelScope.launch {
            val result = when (action) {
                Const.Value.FLASH_ZIP -> {
                    uri ?: return@launch
                    FlashZip(uri, outItems, logItems).exec()
                }
                Const.Value.UNINSTALL -> {
                    showReboot = false
                    MagiskInstaller.Uninstall(outItems, logItems).exec()
                }
                Const.Value.FLASH_MAGISK -> {
                    if (Info.isEmulator)
                        MagiskInstaller.Emulator(outItems, logItems).exec()
                    else
                        MagiskInstaller.Direct(outItems, logItems).exec()
                }
                Const.Value.FLASH_INACTIVE_SLOT -> {
                    showReboot = false
                    MagiskInstaller.SecondSlot(outItems, logItems).exec()
                }
                Const.Value.PATCH_FILE -> {
                    uri ?: return@launch
                    showReboot = false
                    MagiskInstaller.Patch(uri, outItems, logItems).exec()
                }
                Const.Value.DOWNLOAD -> {
                    uri ?: return@launch
                    showReboot = false
                    MagiskInstaller.Download(uri.toString(), outItems, logItems).exec()
                }
                else -> {
                    back()
                    return@launch
                }
            }
            onResult(result)
        }
    }

    private fun onResult(success: Boolean) {
        _state.value = if (success) State.SUCCESS else State.FAILED
    }

    fun onMenuItemClicked(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> savePressed()
        }
        return true
    }

    private fun savePressed() = withExternalRW {
        viewModelScope.launch(Dispatchers.IO) {
            val name = "magisk_install_log_%s.log".format(
                System.currentTimeMillis().toTime(timeFormatStandard)
            )
            val file = MediaStoreUtils.getFile(name)
            file.uri.outputStream().bufferedWriter().use { writer ->
                synchronized(logItems) {
                    logItems.forEach {
                        writer.write(it)
                        writer.newLine()
                    }
                }
            }
            SnackbarEvent(file.toString()).publish()
        }
    }

    fun restartPressed() = reboot()
}
