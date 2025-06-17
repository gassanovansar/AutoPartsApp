package com.ansar.autoPartsApp.data.repository

import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.base.utils.apiCall
import com.ansar.autoPartsApp.data.api.ProductApi
import com.ansar.autoPartsApp.data.mapper.toUI
import com.ansar.autoPartsApp.domain.model.ProductUI
import com.ansar.autoPartsApp.domain.repository.ProductRepository

class ProductRepositoryImpl(private val api: ProductApi) : ProductRepository {

    override suspend fun products(
        title: String,
        brandId: List<Int>,
        categoryId: List<Int>,
        modelId: List<Int>,
        currentPage: Int,
        perPage: Int
    ): Either<Failure, List<ProductUI>> {
        return apiCall(call = {
            api.products(
                title = title.ifBlank { null },
                brandId = brandId.ifEmpty { null },
                categoryId = categoryId.ifEmpty { null },
                modelId = modelId.ifEmpty { null },
                page = currentPage, limit = perPage
            )
        }, mapResponse = {
            it.data?.toUI().orEmpty()
        })
    }

    override suspend fun product(id: Int): Either<Failure, ProductUI> {
        return apiCall(call = {
            api.product(id)
        }, mapResponse = {
            it.data?.toUI() ?: ProductUI.Default
        })
    }
}