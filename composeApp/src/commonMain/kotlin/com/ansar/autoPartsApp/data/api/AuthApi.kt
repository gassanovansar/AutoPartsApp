package com.ansar.autoPartsApp.data.api

import com.ansar.autoPartsApp.data.model.AuthResponse
import com.ansar.autoPartsApp.data.model.BaseResponse
import com.ansar.autoPartsApp.data.model.ProfileResponse
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST

interface AuthApi {


    @POST("v1/auth/sign-in")
    @FormUrlEncoded
    suspend fun auth(
        @Field("username") username: String,
        @Field("password") password: String,
    ): AuthResponse

    @GET("v1/user/get-profile")
    suspend fun getProfile(): BaseResponse<ProfileResponse>
}