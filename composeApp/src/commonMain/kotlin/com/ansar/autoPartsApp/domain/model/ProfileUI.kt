package com.ansar.autoPartsApp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileUI(
    val manager: ManagerUI
)

@Serializable
class ManagerUI(
    val fullName: String,
    val phone: String
)