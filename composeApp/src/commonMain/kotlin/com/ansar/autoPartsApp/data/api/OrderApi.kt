package com.ansar.autoPartsApp.data.api

import com.ansar.autoPartsApp.data.model.BaseProductResponse
import com.ansar.autoPartsApp.data.model.BaseResponse
import com.ansar.autoPartsApp.data.model.OrderResponse
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Query

interface OrderApi {

    @POST("v1/order/create")
    @FormUrlEncoded
    suspend fun createOrder(
        @Field("productId") productId: Int,
        @Field("count") count: Int,
    )

    @GET("v1/order/index")
    suspend fun orders(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): BaseResponse<BaseProductResponse<OrderResponse>>

}