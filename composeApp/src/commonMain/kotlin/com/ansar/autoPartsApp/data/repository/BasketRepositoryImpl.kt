package com.ansar.autoPartsApp.data.repository

import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.base.utils.apiCall
import com.ansar.autoPartsApp.data.api.BasketApi
import com.ansar.autoPartsApp.data.mapper.toUI
import com.ansar.autoPartsApp.domain.model.CartUI
import com.ansar.autoPartsApp.domain.model.CartsUI
import com.ansar.autoPartsApp.domain.model.OrderUI
import com.ansar.autoPartsApp.domain.model.ProductUI
import com.ansar.autoPartsApp.domain.repository.BasketRepository
import kotlin.collections.orEmpty

class BasketRepositoryImpl(private val api: BasketApi) : BasketRepository {

    override suspend fun addCart(productId: Int, count: Int): Either<Failure, Unit> {
        return apiCall { api.addCart(productId, count) }
    }

    override suspend fun deleteCart(productId: Int): Either<Failure, Unit> {
        return apiCall { api.deleteCart(productId) }
    }

    override suspend fun plus(productId: Int): Either<Failure, Unit> {
        return apiCall { api.plus(productId) }
    }

    override suspend fun minus(productId: Int): Either<Failure, Unit> {
        return apiCall { api.minus(productId) }
    }

    override suspend fun clean(): Either<Failure, Unit> {
        return apiCall { api.clean() }
    }

    override suspend fun carts(): Either<Failure, CartsUI> {
        return apiCall(call = {
            api.carts()
        }, mapResponse = {
            CartsUI(
                price = it.data?.price ?: 0,
                cart = it.data?.cart?.map {
                    CartUI(
                        it.product?.toUI() ?: ProductUI.Default,
                        count = it.count ?: 0
                    )
                }.orEmpty()
            )

        })
    }
}