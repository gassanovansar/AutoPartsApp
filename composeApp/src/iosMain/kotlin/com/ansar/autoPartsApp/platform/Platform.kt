package com.ansar.autoPartsApp.platform

import platform.posix.exit
import kotlin.experimental.ExperimentalNativeApi

actual object Platform {
    actual val type: PlatformType = PlatformType.IOS
}

@OptIn(ExperimentalNativeApi::class)
actual val isDebug = kotlin.native.Platform.isDebugBinary
//actual val isDebug = true

actual fun restartApp() {
    exit(0)
}

