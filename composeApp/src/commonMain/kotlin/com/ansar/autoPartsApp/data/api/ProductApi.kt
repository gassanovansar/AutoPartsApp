package com.ansar.autoPartsApp.data.api

import de.jensklingenberg.ktorfit.http.GET

interface ProductApi {

    @GET("v1/product/index")
    suspend fun products()
}