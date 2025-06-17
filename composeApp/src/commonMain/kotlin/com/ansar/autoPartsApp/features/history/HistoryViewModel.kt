package com.ansar.autoPartsApp.features.history

import com.ansar.autoPartsApp.base.utils.BaseScreenModel
import kotlinx.coroutines.flow.MutableStateFlow

class HistoryViewModel : BaseScreenModel<HistoryState, Any>(HistoryState.Default) {

    var canLoad = MutableStateFlow(false)

    init {
//        products()
    }

//
//    private fun products() = intent {
//        reduce { state.copy(page = 1) }
//        launchOperation(operation = { scope ->
//            productsUseCase(
//                scope, ProductsUseCase.Params(
//                    title = state.search,
//                    brandId = state.brand.filter { it.isSelected }.map { it.data.id },
//                    categoryId = state.category.filter { it.isSelected }.map { it.data.id },
//                    currentPage = 1,
//                    perPage = state.limit
//                )
//            )
//        }, success = {
//            canLoad.value = it.isNotEmpty()
//            reduceLocal {
//                state.copy(
//                    products = it
//                )
//            }
//        })
//    }
//
//    fun loadProducts() = intent {
//        if (status.value && !canLoad.value) return@intent
//        reduce { state.copy(page = state.page + 1) }
//        launchOperation(operation = { scope ->
//            productsUseCase(
//                scope, ProductsUseCase.Params(
//                    title = state.search,
//                    brandId = state.brand.filter { it.isSelected }.map { it.data.id },
//                    categoryId = state.category.filter { it.isSelected }.map { it.data.id },
//                    currentPage = state.page,
//                    perPage = state.limit
//                )
//            )
//        }, success = {
//            reduceLocal { state.copy(products = state.products + it) }
//            canLoad.value = it.isNotEmpty()
//        }, loading = {})
//    }

}