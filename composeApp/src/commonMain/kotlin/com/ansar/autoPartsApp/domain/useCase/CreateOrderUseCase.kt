package com.ansar.autoPartsApp.domain.useCase

import com.ansar.autoPartsApp.base.utils.BaseUseCase
import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope

internal class CreateOrderUseCase(private val repository: OrderRepository) :
    BaseUseCase<CreateOrderUseCase.Params, Unit>() {
    class Params(
        val productId: Int,
        val count: Int,
    )

    override suspend fun execute(
        params: Params,
        scope: CoroutineScope
    ): Either<Failure, Unit> {
        return repository.crateOrder(params.productId, params.count)
    }
}