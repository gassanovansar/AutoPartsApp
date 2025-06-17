package com.ansar.autoPartsApp.features.history

import com.ansar.autoPartsApp.base.SelectableDropDownItem
import com.ansar.autoPartsApp.domain.model.ProductUI

data class HistoryState(
    val search: String,
    val brand: List<SelectableDropDownItem>,
    val category: List<SelectableDropDownItem>,
    val products: List<ProductUI>,
    val page: Int,
    val limit: Int
) {
    companion object {
        val Default = HistoryState(
            search = "",
            brand = emptyList(),
            category = emptyList(),
            products = emptyList(),
            page = 1,
            limit = 20
        )
    }
}