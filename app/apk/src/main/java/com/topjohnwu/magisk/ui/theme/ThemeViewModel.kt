package com.mobai.magisk.ui.theme

import com.mobai.magisk.arch.BaseViewModel
import com.mobai.magisk.core.Config
import com.mobai.magisk.dialog.DarkThemeDialog
import com.mobai.magisk.events.RecreateEvent
import com.mobai.magisk.view.TappableHeadlineItem

class ThemeViewModel : BaseViewModel(), TappableHeadlineItem.Listener {

    val themeHeadline = TappableHeadlineItem.ThemeMode

    override fun onItemPressed(item: TappableHeadlineItem) = when (item) {
        is TappableHeadlineItem.ThemeMode -> DarkThemeDialog().show()
    }

    fun saveTheme(theme: Theme) {
        if (!theme.isSelected) {
            Config.themeOrdinal = theme.ordinal
            RecreateEvent().publish()
        }
    }
}
