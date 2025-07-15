package com.ansar.autoPartsApp.features.main

import cafe.adriel.voyager.core.model.screenModelScope
import com.ansar.autoPartsApp.base.SelectableDropDownItem
import com.ansar.autoPartsApp.base.SelectableItem
import com.ansar.autoPartsApp.base.ext.debounce
import com.ansar.autoPartsApp.base.utils.BaseScreenModel
import com.ansar.autoPartsApp.domain.manager.SessionManager
import com.ansar.autoPartsApp.domain.model.ModelUI
import com.ansar.autoPartsApp.domain.useCase.BrandUseCase
import com.ansar.autoPartsApp.domain.useCase.CategoryUseCase
import com.ansar.autoPartsApp.domain.useCase.ModelUseCase
import com.ansar.autoPartsApp.domain.useCase.ProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class MainViewModel : BaseScreenModel<MainState, MainEvent>(MainState.Default) {

    private val brandUseCase: BrandUseCase by inject()
    private val categoryUseCase: CategoryUseCase by inject()
    private val productsUseCase: ProductsUseCase by inject()
    private val modelUseCase: ModelUseCase by inject()
    val sessionManager: SessionManager by inject()

    var canLoad = MutableStateFlow(false)

    init {
        products()
        brand()
        category()
        maodel()
    }


    fun products() = intent {
        reduce { state.copy(page = 1) }
        launchOperation(operation = { scope ->
            productsUseCase(
                scope, ProductsUseCase.Params(
                    title = state.search,
                    brandId = state.brand.filter { it.isSelected }.map { it.data.id },
                    categoryId = state.category.filter { it.isSelected }.map { it.data.id },
                    modelId = state.model.filter { it.isSelected }.map { it.data.id },
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
                    modelId = state.model.filter { it.isSelected }.map { it.data.id },
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

    private fun maodel() = intent {
        launchOperation(operation = { scope ->
            modelUseCase(scope, ModelUseCase.Params())
        }, success = {
            reduceLocal { state.copy(model = it) }
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

    fun changeSearch(value: String) = intent {
        reduce { state.copy(search = value) }
        debounceSearch(value)
    }

    fun changeModel(value: SelectableItem<ModelUI>) = intent {
        reduce {
            state.copy(
                model = state.model.map { it.copy(isSelected = it.data.id == value.data.id) }
            )
        }
        products()
    }

    private val debounceSearch = debounce<String?>(screenModelScope) {
        products()
    }

    fun onClickBrand(it: Boolean) = intent {
        reduce { state.copy(brandSelected = it) }
    }

    fun onClickCatalog(it: Boolean) = intent {
        reduce { state.copy(catalogSelected = it) }
    }
}