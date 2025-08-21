package com.ansar.autoPartsApp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BaseProductResponse<T>(
    val list: List<T>?,
    val pagination: BasePagination?
)

@Serializable
class BasePagination(
    val totalCount: Int?,
    val pageCount: Int?,
    val currentPage: Int?,
    val perPage: Int?,
)


@Serializable
class ProductResponse(
    val id: Int?,
    val title: String?,
    val description: String?,
    val img: String?,
    @SerialName("link_ozon")
    val linkOzon: String?,
    val brand: BrandResponse?,
    val category: BrandResponse?,
    val price: Int?,
    val article: String?,
    val oem: String?,
    val unit: String?,
    val model: BrandResponse?,
    @SerialName("is_available")
    val isAvailable: Int?
)