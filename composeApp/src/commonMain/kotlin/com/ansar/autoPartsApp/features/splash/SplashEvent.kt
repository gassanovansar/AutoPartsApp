package com.ansar.autoPartsApp.features.splash

internal sealed interface SplashEvent {

    object Main : SplashEvent
    object Auth : SplashEvent
}