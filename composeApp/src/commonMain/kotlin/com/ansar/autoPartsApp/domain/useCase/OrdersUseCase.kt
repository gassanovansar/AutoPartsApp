package com.ansar.autoPartsApp.domain.useCase

import com.ansar.autoPartsApp.base.utils.BaseUseCase
import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.model.OrderUI
import com.ansar.autoPartsApp.domain.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope

internal class OrdersUseCase(private val repository: OrderRepository) :
    BaseUseCase<OrdersUseCase.Params, List<OrderUI>>() {
    class Params(
        val currentPage: Int,
        val perPage: Int
    )

    override suspend fun execute(
        params: Params,
        scope: CoroutineScope
    ): Either<Failure, List<OrderUI>> {
        return repository.orders(params.currentPage, params.perPage)
    }
}