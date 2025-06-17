package com.ansar.autoPartsApp.domain.model

import com.ansar.autoPartsApp.uikit.designe.DropDown

class ModelUI(
    override val id: Int,
    override val title: String
) : DropDown {
    companion object {
        val Default = ModelUI(0, "")
    }
}