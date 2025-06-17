package com.ansar.autoPartsApp.data.api

import com.ansar.autoPartsApp.data.model.BaseResponse
import com.ansar.autoPartsApp.data.model.BrandResponse
import com.ansar.autoPartsApp.data.model.ModelResponse
import de.jensklingenberg.ktorfit.http.GET

interface FilterApi {

    @GET("v1/brand/index")
    suspend fun brand(): BaseResponse<List<BrandResponse>>

    @GET("v1/category/index")
    suspend fun category(): BaseResponse<List<BrandResponse>>

    @GET("v1/model/index")
    suspend fun model(): BaseResponse<List<ModelResponse>>
}