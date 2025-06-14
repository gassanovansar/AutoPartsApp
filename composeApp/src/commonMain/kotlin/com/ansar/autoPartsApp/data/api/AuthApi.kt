package com.ansar.autoPartsApp.data.api

import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.POST

interface AuthApi {


    @POST("v1/auth/sign-in")
    @FormUrlEncoded
    suspend fun auth(
        @Field("username") username: String,
        @Field("password") password: String,
    )
}