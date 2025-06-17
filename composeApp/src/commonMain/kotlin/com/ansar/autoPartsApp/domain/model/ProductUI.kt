package com.ansar.autoPartsApp.domain.model

import com.ansar.autoPartsApp.data.model.BrandResponse

//
//class ProductUI(
//    val currentPage:Int,
//    val perPage:Int,
//    val list:List<ProductListUI>
//)

class ProductUI(
    val id: Int,
    val title: String,
    val description: String,
    val img: String,
    val linkOzon: String,
    val article: String,
    val oem: String,
    val unit: String,
    val brand: BrandUI,
    val category: CategoryUI,
    val model: CategoryUI,
    val price: String,
    val isAvailable: Int,
) {
    companion object {
        val Default =
            ProductUI(
                id = 0,
                title = "",
                description = "",
                img = "",
                linkOzon = "",
                brand = BrandUI.Default,
                category = CategoryUI.Default,
                price = "",
                article = "",
                oem = "",
                unit = "",
                model = CategoryUI.Default,
                isAvailable = 0
            )
    }
}