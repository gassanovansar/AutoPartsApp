package com.ansar.autoPartsApp.features.basket

import com.ansar.autoPartsApp.base.utils.BaseScreenModel
import com.ansar.autoPartsApp.domain.useCase.AddCartUseCase
import com.ansar.autoPartsApp.domain.useCase.CartsUseCase
import com.ansar.autoPartsApp.domain.useCase.CleanUseCase
import com.ansar.autoPartsApp.domain.useCase.CreateOrderUseCase
import com.ansar.autoPartsApp.domain.useCase.DeleteCartUseCase
import com.ansar.autoPartsApp.domain.useCase.MinusUseCase
import com.ansar.autoPartsApp.domain.useCase.PlusUseCase
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class BasketViewModel : BaseScreenModel<BasketState, BasketEvent>(BasketState.Default) {
    private val cartsUseCase: CartsUseCase by inject()
    private val deleteCartUseCase: DeleteCartUseCase by inject()

    private val createOrderUseCase: CreateOrderUseCase by inject()
    private val minusUseCase: MinusUseCase by inject()
    private val plusUseCase: PlusUseCase by inject()

    private val cleanUseCase: CleanUseCase by inject()

    init {
        carts()
    }

    private fun carts() = intent {
        launchOperation(operation = { scope ->
            cartsUseCase(scope, CartsUseCase.Params())
        }, success = {
            reduceLocal {
                state.copy(
                    cart = it.cart,
                    price = it.price,
                    emptyText = it.cart.isEmpty()
                )
            }
        })
    }

    fun addCount(id: Int) = intent {
        state.cart.find { it.product.id == id }?.count?.let {
            launchOperation(operation = { scope ->
                plusUseCase(scope, PlusUseCase.Params(id))
            }, success = {
                carts()
//                reduceLocal {
//                    state.copy(
//                        cart = state.cart.map {
//                            if (id == it.product.id) {
//                                it.copy(
//                                    count = it.count + 1
//                                )
//                            } else it
//                        }
//                    )
//                }
            })
        }


    }

    fun createTransaction() = intent {
        launchOperation(operation = { scope ->
            createOrderUseCase(scope, CreateOrderUseCase.Params())
        }, success = { _ ->
            carts()
        })
    }

    fun clean() = intent {
        launchOperation(operation = { scope ->
            cleanUseCase(scope, CleanUseCase.Params())
        }, success = { _ ->
            carts()
        })
    }

    fun delete(id: Int) = intent {
        launchOperation(operation = { scope ->
            deleteCartUseCase(scope, DeleteCartUseCase.Params(id))
        }, success = { _ ->
//            reduceLocal { state.copy(cart = state.cart.filter { it.product.id != id }) }
            carts()
        })
    }

    fun removeCount(id: Int) = intent {
        state.cart.find { it.product.id == id }?.count?.let {
            if (it <= 1) {
                launchOperation(operation = { scope ->
                    deleteCartUseCase(scope, DeleteCartUseCase.Params(id))
                }, success = { _ ->
//                    reduceLocal { state.copy(cart = state.cart.filter { it.product.id != id }) }
                    carts()
                })
            } else {
                launchOperation(operation = { scope ->
                    minusUseCase(scope, MinusUseCase.Params(id))
                }, success = {
//                    reduceLocal {
//                        state.copy(
//                            cart = state.cart.map {
//                                if (id == it.product.id) {
//                                    it.copy(
//                                        count = it.count - 1
//                                    )
//                                } else it
//                            }
//                        )
//                    }
                    carts()
                })

            }
        }
    }
}