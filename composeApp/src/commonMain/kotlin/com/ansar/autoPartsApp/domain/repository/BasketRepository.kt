package com.ansar.autoPartsApp.domain.repository

import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.model.CartUI
import com.ansar.autoPartsApp.domain.model.CartsUI

interface BasketRepository {
    suspend fun addCart(productId: Int, count: Int): Either<Failure, Unit>
    suspend fun deleteCart(productId: Int): Either<Failure, Unit>

    suspend fun plus(productId: Int): Either<Failure, Unit>
    suspend fun minus(productId: Int): Either<Failure, Unit>
    suspend fun carts(): Either<Failure, CartsUI>
    suspend fun clean(): Either<Failure, Unit>
}