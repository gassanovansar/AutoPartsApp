package com.ansar.autoPartsApp.features.product

import com.ansar.autoPartsApp.base.utils.BaseScreenModel
import com.ansar.autoPartsApp.domain.useCase.ProductUseCase
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent

class ProductViewModel : BaseScreenModel<ProductState, ProductEvent>(ProductState.Default) {

    private val productUseCase: ProductUseCase by inject()

    fun getProduct(id: Int) = intent {
        launchOperation(operation = { scope ->
            productUseCase(scope, ProductUseCase.Params(id))
        }, success = {
            reduceLocal { state.copy(product = it) }
        })
    }
}