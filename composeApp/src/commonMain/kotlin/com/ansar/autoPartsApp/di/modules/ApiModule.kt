package com.ansar.autoPartsApp.di.modules


import com.ansar.autoPartsApp.data.api.AuthApi
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.dsl.module

internal val apiModule = module {
    factory { get<Ktorfit>().create<AuthApi>() }

}