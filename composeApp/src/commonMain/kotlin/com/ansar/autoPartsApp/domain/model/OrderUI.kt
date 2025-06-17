package com.ansar.autoPartsApp.domain.model



data class OrderUI(
    val id: Int,
    val product: ProductUI,
    val count: Int,
    val price: String,
    val status: String
)