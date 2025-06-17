package com.ansar.autoPartsApp.data.api

import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.POST

interface OrderApi {

    @POST("v1/order/create")
    @FormUrlEncoded
    suspend fun createOrder(
        @Field("productId") productId: Int,
        @Field("count") count: Int,
    )

}