package com.ansar.autoPartsApp.features.product

import com.ansar.autoPartsApp.domain.model.ProductUI

data class ProductState(
    val product: ProductUI,
    val count: String
) {
    companion object {
        val Default = ProductState(ProductUI.Default, "1")
    }
}