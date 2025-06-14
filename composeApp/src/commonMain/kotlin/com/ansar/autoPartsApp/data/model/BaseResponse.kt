package com.ansar.autoPartsApp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BaseResponse<T>(
    @SerialName("data")
    val data: T? = null,
)