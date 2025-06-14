package com.ansar.autoPartsApp.di.modules

import com.ansar.autoPartsApp.data.repository.*
import com.ansar.autoPartsApp.domain.repository.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val repositoryModule = module {
    factoryOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    factoryOf(::FilterRepositoryImpl) { bind<FilterRepository>() }

}