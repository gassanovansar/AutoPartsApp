package com.ansar.autoPartsApp.features.profile

import com.ansar.autoPartsApp.base.utils.BaseScreenModel
import com.ansar.autoPartsApp.domain.manager.SessionManager
import com.ansar.autoPartsApp.domain.useCase.GetProfileUseCase
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent

class ProfileViewModel : BaseScreenModel<ProfileState, Any>(ProfileState.Default) {
    val sessionManager: SessionManager by inject()
    private val getProfileUseCase: GetProfileUseCase by inject()

    fun getProfile() = intent {
        launchOperation(operation = { scope ->
            getProfileUseCase(scope, GetProfileUseCase.Params())
        })
    }
}