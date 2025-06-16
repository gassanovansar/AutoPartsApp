package com.ansar.autoPartsApp.features.product

import com.ansar.autoPartsApp.domain.model.ProductUI

data class ProductState(val product: ProductUI) {
    companion object {
        val Default = ProductState(ProductUI.Default)
    }
}