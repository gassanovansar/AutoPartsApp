package com.ansar.autoPartsApp.data.repository

import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.base.utils.apiCall
import com.ansar.autoPartsApp.data.api.OrderApi
import com.ansar.autoPartsApp.domain.repository.OrderRepository

class OrderRepositoryImpl(private val api: OrderApi) : OrderRepository {
    override suspend fun crateOrder(productId: Int, count: Int): Either<Failure, Unit> {
        return apiCall { api.createOrder(productId, count) }
    }
}