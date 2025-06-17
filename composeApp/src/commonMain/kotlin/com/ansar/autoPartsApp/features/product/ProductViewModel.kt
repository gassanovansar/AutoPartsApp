package com.ansar.autoPartsApp.features.product

import com.ansar.autoPartsApp.base.utils.BaseScreenModel
import com.ansar.autoPartsApp.domain.manager.Notification
import com.ansar.autoPartsApp.domain.useCase.CreateOrderUseCase
import com.ansar.autoPartsApp.domain.useCase.ProductUseCase
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class ProductViewModel : BaseScreenModel<ProductState, ProductEvent>(ProductState.Default) {

    private val productUseCase: ProductUseCase by inject()
    private val createOrderUseCase: CreateOrderUseCase by inject()

    fun getProduct(id: Int) = intent {
        launchOperation(operation = { scope ->
            productUseCase(scope, ProductUseCase.Params(id))
        }, success = {
            reduceLocal { state.copy(product = it) }
        })
    }

    fun createOrder(id: Int) = intent {
        if (state.count.isNotBlank() && state.count.toInt() > 0) {
            launchOperation(operation = { scope ->
                createOrderUseCase(scope, CreateOrderUseCase.Params(id, state.count.toInt()))
            }, success = {
                showGlobalMessage(Notification.Success(message = "Заказ принят"))
            })
        } else {
            showGlobalMessage(Notification.Failure(message = "Необходимо указать количество"))
        }
    }

    fun addCount() = intent {
        reduce { state.copy(
            count = (state.count.toInt() + 1).toString()
        ) }
    }
    fun removeCount() = intent {
        if(state.count.toInt()>0){
            reduce { state.copy(
                count = (state.count.toInt() - 1).toString()
            ) }
        }

    }

    fun changeCount(it: String) = intent {
        reduce { state.copy(count = it) }

    }
}