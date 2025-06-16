package com.ansar.autoPartsApp.domain.model

import com.ansar.autoPartsApp.uikit.designe.DropDown

class CategoryUI(
    override val id: Int,
    override val title: String
) : DropDown {
    companion object {
        val Default = CategoryUI(0, "")
    }
}