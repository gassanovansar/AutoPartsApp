package com.ansar.autoPartsApp.domain.model

data class CartsUI(
    val price: Int,
    val cart: List<CartUI>
)

data class CartUI(
    val product: ProductUI,
    val count: Int,
)

