package com.ansar.autoPartsApp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ProfileResponse(
    val manager: ManagerResponse?
)

@Serializable
class ManagerResponse(
    @SerialName("fullname")
    val fullName: String?,
    val phone: String?
)