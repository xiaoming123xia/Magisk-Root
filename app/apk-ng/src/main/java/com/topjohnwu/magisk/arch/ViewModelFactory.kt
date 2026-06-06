package com.mobai.magisk.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobai.magisk.core.di.ServiceLocator
import com.mobai.magisk.ui.home.HomeViewModel
import com.mobai.magisk.ui.install.InstallViewModel
import com.mobai.magisk.ui.log.LogViewModel
import com.mobai.magisk.ui.superuser.SuperuserViewModel
import com.mobai.magisk.ui.surequest.SuRequestViewModel

object VMFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            HomeViewModel::class.java -> HomeViewModel(ServiceLocator.networkService)
            LogViewModel::class.java -> LogViewModel(ServiceLocator.logRepo)
            SuperuserViewModel::class.java -> SuperuserViewModel(ServiceLocator.policyDB)
            InstallViewModel::class.java ->
                InstallViewModel(ServiceLocator.networkService)
            SuRequestViewModel::class.java ->
                SuRequestViewModel(ServiceLocator.policyDB, ServiceLocator.timeoutPrefs)
            else -> modelClass.newInstance()
        } as T
    }
}
