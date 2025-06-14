package com.ansar.autoPartsApp.di.modules


import com.ansar.autoPartsApp.domain.manager.EnvManager
import com.ansar.autoPartsApp.domain.manager.EnvManagerImpl
import com.ansar.autoPartsApp.domain.manager.NotificationManager
import com.ansar.autoPartsApp.domain.manager.NotificationManagerImpl
import com.ansar.autoPartsApp.domain.manager.SessionManager
import com.ansar.autoPartsApp.domain.manager.SessionManagerImpl
import com.ansar.autoPartsApp.domain.manager.store.TokenStore
import com.ansar.autoPartsApp.domain.manager.store.TokenStoreImpl
import com.russhwolf.settings.Settings
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val managerModule = module {
    singleOf(::SessionManagerImpl) { bind<SessionManager>() }
    singleOf(::EnvManagerImpl) { bind<EnvManager>() }
    singleOf(::TokenStoreImpl) { bind<TokenStore>() }
    singleOf(::NotificationManagerImpl) { bind<NotificationManager>() }

    singleOf(::Settings)
}