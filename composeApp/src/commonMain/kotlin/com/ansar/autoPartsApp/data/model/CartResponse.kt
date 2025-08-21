package com.ansar.autoPartsApp.data.model

import kotlinx.serialization.Serializable

@Serializable
class CartsResponse(
    val price: Int?,
    val cart: List<CartResponse>
)

@Serializable
class CartResponse(
    val product: ProductResponse?,
    val count: Int?,
)

