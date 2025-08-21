package com.ansar.autoPartsApp.data.model

import kotlinx.serialization.Serializable

@Serializable
class OrderResponse(
    val id: Int?,
    val price: String?,
    val status: String?,
    val cart: List<CartResponse>,
)

