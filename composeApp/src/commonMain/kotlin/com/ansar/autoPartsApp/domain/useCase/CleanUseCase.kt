package com.ansar.autoPartsApp.domain.useCase

import com.ansar.autoPartsApp.base.utils.BaseUseCase
import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.repository.BasketRepository
import com.ansar.autoPartsApp.domain.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope

internal class CleanUseCase(private val repository: BasketRepository) :
    BaseUseCase<CleanUseCase.Params, Unit>() {
    class Params(
    )

    override suspend fun execute(
        params: Params,
        scope: CoroutineScope
    ): Either<Failure, Unit> {
        return repository.clean()
    }
}