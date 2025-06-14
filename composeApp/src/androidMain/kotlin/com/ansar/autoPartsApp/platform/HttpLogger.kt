package com.ansar.autoPartsApp.platform

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.ansar.autoPartsApp.di.KoinInjector
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttpConfig

actual fun HttpClientConfig<*>.httpLogger() {
    engine {
        if (this is OkHttpConfig && isDebug) {
            try {
                val context by KoinInjector.koin.inject<Context>()
                val chuckerInterceptor = ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
                this.addInterceptor(chuckerInterceptor)
            } catch (e: Exception) {
                println()
            }
        }
    }
}