package com.ansar.autoPartsApp.data.api

import com.ansar.autoPartsApp.data.model.BaseResponse
import com.ansar.autoPartsApp.data.model.CartResponse
import com.ansar.autoPartsApp.data.model.CartsResponse
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST

interface BasketApi {
    @GET("v1/cart/index")
    suspend fun carts(): BaseResponse<CartsResponse>


    @POST("v1/cart/add")
    @FormUrlEncoded
    suspend fun addCart(
        @Field("productId") productId: Int,
        @Field("count") count: Int,
    )

    @POST("v1/cart/delete")
    @FormUrlEncoded
    suspend fun deleteCart(
        @Field("productId") productId: Int
    )

    @POST("v1/cart/plus")
    @FormUrlEncoded
    suspend fun plus(
        @Field("productId") productId: Int,
    )

    @POST("v1/cart/minus")
    @FormUrlEncoded
    suspend fun minus(
        @Field("productId") productId: Int,
    )
       @POST("v1/cart/clean")
    suspend fun clean()



}