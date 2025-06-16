package com.ansar.autoPartsApp.data.mapper

import com.ansar.autoPartsApp.data.model.BaseProductResponse
import com.ansar.autoPartsApp.data.model.ProductResponse
import com.ansar.autoPartsApp.domain.model.BrandUI
import com.ansar.autoPartsApp.domain.model.CategoryUI
import com.ansar.autoPartsApp.domain.model.ProductUI

fun BaseProductResponse.toUI(): List<ProductUI> {
    return this.list?.map {
        it.toUI()
    }.orEmpty()
}

fun ProductResponse.toUI(): ProductUI {
    val it = this
    return ProductUI(
        id = it.id ?: 0,
        title = it.title.orEmpty(),
        description = it.description.orEmpty(),
        img = it.img.orEmpty(),
        linkOzon = it.linkOzon.orEmpty(),
        brand = BrandUI(id = it.brand?.id ?: 0, title = it.brand?.title.orEmpty()),
        category = CategoryUI(it.category?.id ?: 0, it.category?.title.orEmpty()),
        price = it.price.orEmpty()
    )
}