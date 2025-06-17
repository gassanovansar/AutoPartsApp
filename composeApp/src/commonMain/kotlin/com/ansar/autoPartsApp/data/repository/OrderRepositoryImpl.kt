package com.ansar.autoPartsApp.data.repository

import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.base.utils.apiCall
import com.ansar.autoPartsApp.data.api.OrderApi
import com.ansar.autoPartsApp.data.mapper.toUI
import com.ansar.autoPartsApp.data.model.OrderResponse
import com.ansar.autoPartsApp.domain.model.OrderUI
import com.ansar.autoPartsApp.domain.model.ProductUI
import com.ansar.autoPartsApp.domain.repository.OrderRepository

class OrderRepositoryImpl(private val api: OrderApi) : OrderRepository {
    override suspend fun crateOrder(productId: Int, count: Int): Either<Failure, Unit> {
        return apiCall { api.createOrder(productId, count) }
    }

    override suspend fun orders(currentPage: Int, perPage: Int): Either<Failure, List<OrderUI>> {
        return apiCall(call = {
            api.orders(currentPage, perPage)
        }, mapResponse = {
            it.data?.list?.map {
                OrderUI(
                    id = it.id ?: 0,
                    product = it.product?.toUI() ?: ProductUI.Default,
                    count = it.count ?: 0,
                    price = it.price.orEmpty(),
                    status = it.status.orEmpty()
                )
            }.orEmpty()
        })
    }
}