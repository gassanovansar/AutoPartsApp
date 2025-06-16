package com.ansar.autoPartsApp.domain.useCase

import com.ansar.autoPartsApp.base.utils.BaseUseCase
import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.model.ProductUI
import com.ansar.autoPartsApp.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope

internal class ProductUseCase(private val repository: ProductRepository) :
    BaseUseCase<ProductUseCase.Params, ProductUI>() {
    class Params(val id: Int)

    override suspend fun execute(
        params: Params,
        scope: CoroutineScope
    ): Either<Failure, ProductUI> {
        return repository.product(params.id)
    }
}