package com.ansar.autoPartsApp.data.model

import kotlinx.serialization.Serializable

@Serializable
class AuthResponse(
    val token: String?
)