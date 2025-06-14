package com.ansar.autoPartsApp.features.main

import com.ansar.autoPartsApp.base.SelectableDropDownItem
import com.ansar.autoPartsApp.base.utils.BaseScreenModel
import com.ansar.autoPartsApp.domain.useCase.BrandUseCase
import com.ansar.autoPartsApp.domain.useCase.CategoryUseCase
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class MainViewModel : BaseScreenModel<MainState, MainEvent>(MainState.Default) {

    private val brandUseCase: BrandUseCase by inject()
    private val categoryUseCase: CategoryUseCase by inject()

    init {
        brand()
        category()
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