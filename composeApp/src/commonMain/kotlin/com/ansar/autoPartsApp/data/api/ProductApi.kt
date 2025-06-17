package com.ansar.autoPartsApp.data.api

import com.ansar.autoPartsApp.data.model.BaseProductResponse
import com.ansar.autoPartsApp.data.model.BaseResponse
import com.ansar.autoPartsApp.data.model.ProductResponse
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

interface ProductApi {

    @GET("v1/product/index")
    suspend fun products(
        @Query("title") title: String?,
        @Query("brandId") brandId: List<Int>?,
        @Query("categoryId") categoryId: List<Int>?,
        @Query("modelId") modelId: List<Int>?,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): BaseResponse<BaseProductResponse<ProductResponse>>

    @GET("/v1/product/get")
    suspend fun product(
        @Query("productId") productId: Int,
    ): BaseResponse<ProductResponse>


}