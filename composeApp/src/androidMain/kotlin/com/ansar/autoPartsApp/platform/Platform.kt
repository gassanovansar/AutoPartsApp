package com.ansar.autoPartsApp.platform

import android.app.Activity
import com.ansar.autoPartsApp.di.KoinInjector
import com.russhwolf.settings.BuildConfig


actual object Platform {
    actual val type: PlatformType = PlatformType.ANDROID
}

actual val isDebug = BuildConfig.DEBUG

actual fun restartApp(){
    val activity by KoinInjector.koin.inject<Activity>()
    val intent = activity.intent
    activity.finish()
    activity.startActivity(intent)
}