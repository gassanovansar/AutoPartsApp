package com.ansar.autoPartsApp.domain.model

import com.ansar.autoPartsApp.data.model.CartsResponse

class OrderUI(
    val id: Int,
    val price: String,
    val status: String,
    val cart: List<CartUI>,
)