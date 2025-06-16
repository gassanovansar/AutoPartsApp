package com.ansar.autoPartsApp.data.model

import kotlinx.serialization.Serializable

@Serializable
class CategoryResponse(
    val id: Int?,
    val title: String?,
    val description: String?,
    val img: String?,
)