package com.ansar.autoPartsApp.domain.repository

import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.model.OrderUI

interface OrderRepository {
    suspend fun crateOrder(
    ): Either<Failure, Unit>

    suspend fun orders(
        currentPage: Int,
        perPage: Int
    ): Either<Failure, List<OrderUI>>
}