package com.ansar.autoPartsApp.data.repository

import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.base.utils.apiCall
import com.ansar.autoPartsApp.data.api.OrderApi
import com.ansar.autoPartsApp.data.mapper.toUI
import com.ansar.autoPartsApp.domain.model.CartUI
import com.ansar.autoPartsApp.domain.model.CartsUI
import com.ansar.autoPartsApp.domain.model.OrderUI
import com.ansar.autoPartsApp.domain.model.ProductUI
import com.ansar.autoPartsApp.domain.repository.OrderRepository
import kotlin.collections.orEmpty

class OrderRepositoryImpl(private val api: OrderApi) : OrderRepository {
    override suspend fun crateOrder(): Either<Failure, Unit> {
        return apiCall { api.createOrder() }
    }

    override suspend fun orders(currentPage: Int, perPage: Int): Either<Failure, List<OrderUI>> {
        return apiCall(call = {
            api.orders(currentPage, perPage)
        }, mapResponse = {
            it.data?.list?.map {
                OrderUI(
                    id = it.id ?: 0,
                    price = it.price.orEmpty(),
                    status = it.status.orEmpty(),
                    cart = it.cart.map {
                        CartUI(
                            it.product?.toUI() ?: ProductUI.Default,
                            count = it.count ?: 0
                        )
                    }
                )
            }.orEmpty()
        })
    }
}