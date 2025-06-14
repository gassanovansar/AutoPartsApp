package com.ansar.autoPartsApp.di

import com.ansar.autoPartsApp.di.modules.apiModule
import com.ansar.autoPartsApp.di.modules.managerModule
import com.gntit.capperru.di.modules.networkModule
import com.ansar.autoPartsApp.di.modules.repositoryModule
import com.ansar.autoPartsApp.di.modules.useCaseModule
import org.koin.core.context.startKoin
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module

object KoinInjector {

    val koinApp = startKoin {
        loadKoinModules(
            listOf(
                apiModule,
                networkModule,
                useCaseModule,
                repositoryModule,
                managerModule,
//                platformPermissionsModule
            )
        )
    }

    internal val koin = koinApp.koin

    fun init(modules: List<Module> = listOf()) {
        koinApp.koin.loadModules(modules)
    }


}