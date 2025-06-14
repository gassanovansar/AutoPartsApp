package com.ansar.autoPartsApp.di.modules

import com.ansar.autoPartsApp.data.repository.AuthRepositoryImpl
import com.ansar.autoPartsApp.domain.repository.AuthRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val repositoryModule = module {
    factoryOf(::AuthRepositoryImpl) { bind<AuthRepository>() }

}