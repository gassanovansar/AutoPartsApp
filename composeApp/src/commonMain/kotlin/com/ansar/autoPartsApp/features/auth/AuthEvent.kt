package com.ansar.autoPartsApp.features.auth

sealed interface AuthEvent {
    data object Success : AuthEvent
}