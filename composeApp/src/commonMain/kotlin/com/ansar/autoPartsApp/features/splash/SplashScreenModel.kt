package com.ansar.autoPartsApp.features.splash

import com.ansar.autoPartsApp.base.utils.BaseScreenModel
import com.ansar.autoPartsApp.domain.manager.SessionManager
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent

internal class SplashScreenModel : BaseScreenModel<Any, SplashEvent>(Any()) {

    private val sessionManager: SessionManager by inject()

    init {
        navigate()
    }

    private fun navigate() = intent {
        if (sessionManager.isAuth) {
            postSideEffectLocal(SplashEvent.Main)
        } else {
            postSideEffectLocal(SplashEvent.Auth)
        }
    }
}