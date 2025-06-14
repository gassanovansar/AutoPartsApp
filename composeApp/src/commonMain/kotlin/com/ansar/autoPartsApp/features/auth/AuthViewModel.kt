package com.ansar.autoPartsApp.features.auth

import com.ansar.autoPartsApp.base.ext.isEmail
import com.ansar.autoPartsApp.base.utils.BaseScreenModel
import com.ansar.autoPartsApp.domain.manager.Notification
import com.ansar.autoPartsApp.domain.useCase.AuthUseCase
import org.koin.core.component.inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class AuthViewModel : BaseScreenModel<AuthState, AuthEvent>(AuthState.Default) {
    private val authUseCase: AuthUseCase by inject()

    fun auth() = intent {
        if (state.isValid) {
            reduce {
                state.copy(
                    hasPasswordError = false, hasLoginError = false
                )
            }
            launchOperation(operation = { scope ->
                authUseCase(scope, AuthUseCase.Params(state.login, state.password))
            }, success = {
                postSideEffectLocal(AuthEvent.Success)
            })
        } else {
            val hasLoginError = state.login.isBlank()
            val hasPasswordError = state.password.isBlank() || state.password.length < 8
            if (hasPasswordError && hasLoginError) {
                showGlobalMessage(Notification.Failure(message = "Неверный логин и пароль"))
            }else if (hasPasswordError){
                showGlobalMessage(Notification.Failure(message = "Пароль должен содержать не менее 8 символов"))

            }else if (hasLoginError){
                showGlobalMessage(Notification.Failure(message = "Неверный логин"))
            }
            reduce {
                state.copy(
                    hasLoginError = hasLoginError,
                    hasPasswordError = hasPasswordError,
                )
            }
        }
    }

    fun changeLogin(login: String) = intent {
        reduce { state.copy(login = login, hasLoginError = false) }
    }

    fun changePassword(password: String) = intent {
        reduce { state.copy(password = password, hasPasswordError = false) }
    }
}