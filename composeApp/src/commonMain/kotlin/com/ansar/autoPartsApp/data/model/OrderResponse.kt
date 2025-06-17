package com.ansar.autoPartsApp.data.model

import kotlinx.serialization.Serializable

@Serializable
class OrderResponse(
    val id: Int?,
    val product: ProductResponse?,
    val count: Int?,
    val price: String?,
    val status: String?
)