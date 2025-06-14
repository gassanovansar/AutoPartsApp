package com.ansar.autoPartsApp.features.auth

data class AuthState(
    val login: String,
    val hasLoginError: Boolean,
    val password: String,
    val hasPasswordError: Boolean
) {

    val isValid = login.isNotBlank() && password.length >= 8

    companion object {
        val Default = AuthState(
            login = "",
            hasLoginError = false,
            password = "",
            hasPasswordError = false
        )
    }
}
