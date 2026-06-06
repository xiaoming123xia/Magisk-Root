package com.mobai.magisk.view

import com.mobai.magisk.R
import com.mobai.magisk.databinding.DiffItem
import com.mobai.magisk.databinding.ItemWrapper
import com.mobai.magisk.databinding.RvItem

class TextItem(override val item: Int) : RvItem(), DiffItem<TextItem>, ItemWrapper<Int> {
    override val layoutRes = R.layout.item_text
}
