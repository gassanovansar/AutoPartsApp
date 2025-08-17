package com.ansar.autoPartsApp.domain.useCase

import com.ansar.autoPartsApp.base.utils.BaseUseCase
import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.repository.BasketRepository
import com.ansar.autoPartsApp.domain.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope

internal class PlusUseCase(private val repository: BasketRepository) :
    BaseUseCase<PlusUseCase.Params, Unit>() {
    class Params(
        val productId: Int
    )

    override suspend fun execute(
        params: Params,
        scope: CoroutineScope
    ): Either<Failure, Unit> {
        return repository.plus(params.productId)
    }
}