package com.mobai.magisk.ui.flash

import com.mobai.magisk.R
import com.mobai.magisk.databinding.DiffItem
import com.mobai.magisk.databinding.ItemWrapper
import com.mobai.magisk.databinding.RvItem

class ConsoleItem(
    override val item: String
) : RvItem(), DiffItem<ConsoleItem>, ItemWrapper<String> {
    override val layoutRes = R.layout.item_console_md2
}
