package com.ansar.autoPartsApp.features.main

import com.ansar.autoPartsApp.base.SelectableDropDownItem

data class MainState(
    val brand: List<SelectableDropDownItem>,
    val category: List<SelectableDropDownItem>
) {
    companion object {
        val Default = MainState(brand = emptyList(), category = emptyList())
    }
}