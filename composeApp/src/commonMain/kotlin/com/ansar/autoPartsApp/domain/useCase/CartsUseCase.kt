package com.ansar.autoPartsApp.domain.useCase

import com.ansar.autoPartsApp.base.utils.BaseUseCase
import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.model.CartUI
import com.ansar.autoPartsApp.domain.model.CartsUI
import com.ansar.autoPartsApp.domain.repository.BasketRepository
import kotlinx.coroutines.CoroutineScope

internal class CartsUseCase(private val repository: BasketRepository) :
    BaseUseCase<CartsUseCase.Params, CartsUI>() {
    override suspend fun execute(
        params: Params,
        scope: CoroutineScope
    ): Either<Failure, CartsUI> {
        return repository.carts()
    }

    class Params
}