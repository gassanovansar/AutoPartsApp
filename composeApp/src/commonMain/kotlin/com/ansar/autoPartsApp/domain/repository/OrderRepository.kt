package com.ansar.autoPartsApp.domain.repository

import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure

interface OrderRepository {
    suspend fun crateOrder(
        productId: Int,
        count: Int,
    ): Either<Failure, Unit>
}