package com.mobai.magisk.ui.log

import com.mobai.magisk.R
import com.mobai.magisk.databinding.DiffItem
import com.mobai.magisk.databinding.ItemWrapper
import com.mobai.magisk.databinding.ObservableRvItem

class LogRvItem(
    override val item: String
) : ObservableRvItem(), DiffItem<LogRvItem>, ItemWrapper<String> {
    override val layoutRes = R.layout.item_log_textview
}
