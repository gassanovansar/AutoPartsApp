package com.ansar.autoPartsApp.features.history

import com.ansar.autoPartsApp.base.utils.BaseScreenModel
import com.ansar.autoPartsApp.domain.useCase.OrdersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class HistoryViewModel : BaseScreenModel<HistoryState, Any>(HistoryState.Default) {

    var canLoad = MutableStateFlow(false)

    private val ordersUseCase: OrdersUseCase by inject()

    init {
        orders()
    }


    private fun orders() = intent {
        reduce { state.copy(page = 1) }
        launchOperation(operation = { scope ->
            ordersUseCase(
                scope, OrdersUseCase.Params(
                    currentPage = 1,
                    perPage = state.limit
                )
            )
        }, success = {
            canLoad.value = it.isNotEmpty()
            reduceLocal {
                state.copy(
                    orders = it
                )
            }
        })
    }

    fun loadOrders() = intent {
        if (status.value && !canLoad.value) return@intent
        reduce { state.copy(page = state.page + 1) }
        launchOperation(operation = { scope ->
            ordersUseCase(
                scope, OrdersUseCase.Params(
                    currentPage = state.page,
                    perPage = state.limit
                )
            )
        }, success = {
            reduceLocal { state.copy(orders = state.orders + it) }
            canLoad.value = it.isNotEmpty()
        }, loading = {})
    }

}