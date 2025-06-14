package com.ansar.autoPartsApp.di

import com.ansar.autoPartsApp.platform.MultipartManager
import com.ansar.autoPartsApp.platform.MultipartManagerImpl
import org.koin.dsl.module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf

internal val platformViewModel = module {
    singleOf(::MultipartManagerImpl) { bind<MultipartManager>() }

}