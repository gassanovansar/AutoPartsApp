package com.ansar.autoPartsApp.domain.useCase

import com.ansar.autoPartsApp.base.utils.BaseUseCase
import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.model.ProductUI
import com.ansar.autoPartsApp.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope

internal class ProductsUseCase(private val repository: ProductRepository) :
    BaseUseCase<ProductsUseCase.Params, List<ProductUI>>() {
    class Params(
        val title: String,
        val brandId: List<Int>,
        val categoryId: List<Int>,
        val modelId: List<Int>,
        val currentPage: Int,
        val perPage: Int,
    )

    override suspend fun execute(
        params: Params,
        scope: CoroutineScope
    ): Either<Failure, List<ProductUI>> {
        return repository.products(
            title = params.title,
            brandId = params.brandId,
            categoryId = params.categoryId,
            modelId = params.modelId.filter { it != 0 },
            currentPage = params.currentPage,
            perPage = params.perPage
        )
    }
}