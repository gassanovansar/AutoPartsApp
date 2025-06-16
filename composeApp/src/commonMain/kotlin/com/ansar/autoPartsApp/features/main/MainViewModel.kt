package com.ansar.autoPartsApp.features.main

import com.ansar.autoPartsApp.base.SelectableDropDownItem
import com.ansar.autoPartsApp.base.utils.BaseScreenModel
import com.ansar.autoPartsApp.domain.useCase.BrandUseCase
import com.ansar.autoPartsApp.domain.useCase.CategoryUseCase
import com.ansar.autoPartsApp.domain.useCase.ProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class MainViewModel : BaseScreenModel<MainState, MainEvent>(MainState.Default) {

    private val brandUseCase: BrandUseCase by inject()
    private val categoryUseCase: CategoryUseCase by inject()
    private val productsUseCase: ProductsUseCase by inject()

    var canLoad = MutableStateFlow(false)

    init {
        products()
        brand()
        category()
    }


    private fun products() = intent {
        reduce { state.copy(page = 1) }
        launchOperation(operation = { scope ->
            productsUseCase(
                scope, ProductsUseCase.Params(
                    title = state.search,
                    brandId = state.brand.filter { it.isSelected }.map { it.data.id },
                    categoryId = state.category.filter { it.isSelected }.map { it.data.id },
                    currentPage = 1,
                    perPage = state.limit
                )
            )
        }, success = {
            canLoad.value = it.isNotEmpty()
            reduceLocal {
                state.copy(
                    products = it
                )
            }
        })
    }

    fun loadProducts() = intent {
        if (status.value && !canLoad.value) return@intent
        reduce { state.copy(page = state.page + 1) }
        launchOperation(operation = { scope ->
            productsUseCase(
                scope, ProductsUseCase.Params(
                    title = state.search,
                    brandId = state.brand.filter { it.isSelected }.map { it.data.id },
                    categoryId = state.category.filter { it.isSelected }.map { it.data.id },
                    currentPage = state.page,
                    perPage = state.limit
                )
            )
        }, success = {
            reduceLocal { state.copy(products = state.products + it) }
            canLoad.value = it.isNotEmpty()
        }, loading = {})
    }

    private fun brand() = intent {
        launchOperation(operation = { scope ->
            brandUseCase(scope, BrandUseCase.Params())
        }, success = {
            reduceLocal { state.copy(brand = it) }
        })
    }

    private fun category() = intent {
        launchOperation(operation = { scope ->
            categoryUseCase(scope, CategoryUseCase.Params())
        }, success = {
            reduceLocal { state.copy(category = it) }
        })
    }

    fun selectBrand(brand: SelectableDropDownItem) = intent {
        reduce {
            state.copy(
                brand = state.brand.map { if (brand == it) it.copy(isSelected = !it.isSelected) else it }
            )
        }
    }

    fun selectCategory(brand: SelectableDropDownItem) = intent {
        reduce {
            state.copy(
                category = state.category.map { if (brand == it) it.copy(isSelected = !it.isSelected) else it }
            )
        }
    }

    fun clearFilter() = intent {
        reduce {
            state.copy(
                brand = state.brand.map { it.copy(isSelected = false) },
                category = state.category.map { it.copy(isSelected = false) }
            )
        }

    }
}