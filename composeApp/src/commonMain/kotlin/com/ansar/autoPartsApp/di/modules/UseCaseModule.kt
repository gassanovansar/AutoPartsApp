package com.ansar.autoPartsApp.di.modules


import com.ansar.autoPartsApp.domain.useCase.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


internal val useCaseModule = module {

    factoryOf(::AuthUseCase)
    factoryOf(::BrandUseCase)
    factoryOf(::CategoryUseCase)
    factoryOf(::ProductsUseCase)
    factoryOf(::ProductUseCase)
    factoryOf(::ModelUseCase)
    factoryOf(::CreateOrderUseCase)
    factoryOf(::OrdersUseCase)
    factoryOf(::AddCartUseCase)
    factoryOf(::CartsUseCase)
    factoryOf(::DeleteCartUseCase)
    factoryOf(::PlusUseCase)
    factoryOf(::MinusUseCase)
    factoryOf(::CleanUseCase)


}