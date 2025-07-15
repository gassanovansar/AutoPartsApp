package com.ansar.autoPartsApp.features.main

import com.ansar.autoPartsApp.base.SelectableDropDownItem
import com.ansar.autoPartsApp.base.SelectableItem
import com.ansar.autoPartsApp.domain.model.ModelUI
import com.ansar.autoPartsApp.domain.model.ProductUI

data class MainState(
    val search: String,
    val brand: List<SelectableDropDownItem>,
    val category: List<SelectableDropDownItem>,
    val model: List<SelectableItem<ModelUI>>,
    val products: List<ProductUI>,
    val page: Int,
    val limit: Int,
    val catalogSelected: Boolean,
    val brandSelected: Boolean,

    ) {
    companion object {
        val Default = MainState(
            search = "",
            brand = emptyList(),
            category = emptyList(),
            model = emptyList(),
            products = emptyList(),
            page = 1,
            limit = 20,
            catalogSelected = false,
            brandSelected = false
        )
    }
}