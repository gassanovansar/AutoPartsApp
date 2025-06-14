package com.ansar.autoPartsApp.di.modules


import com.ansar.autoPartsApp.domain.useCase.AuthUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


internal val useCaseModule = module {

    /**
     * SSO
     */
    factoryOf(::AuthUseCase)


}