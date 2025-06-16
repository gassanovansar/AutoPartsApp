package com.ansar.autoPartsApp.domain.repository

import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.model.ProductUI

interface ProductRepository {


    suspend fun products(
        title: String,
        brandId: List<Int>,
        categoryId: List<Int>,
        currentPage: Int, perPage: Int
    ): Either<Failure, List<ProductUI>>

    suspend fun product(id: Int): Either<Failure, ProductUI>
}