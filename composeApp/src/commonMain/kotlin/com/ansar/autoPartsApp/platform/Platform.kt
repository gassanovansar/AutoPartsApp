package com.ansar.autoPartsApp.platform

enum class PlatformType {
    ANDROID,
    IOS
}

expect object Platform {
    val type: PlatformType
}

expect val isDebug: Boolean

expect fun restartApp()