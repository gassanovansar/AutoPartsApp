package com.ansar.autoPartsApp.data.repository

import com.ansar.autoPartsApp.base.SelectableDropDownItem
import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.base.utils.apiCall
import com.ansar.autoPartsApp.data.api.FilterApi
import com.ansar.autoPartsApp.data.mapper.toUI
import com.ansar.autoPartsApp.domain.repository.FilterRepository

class FilterRepositoryImpl(private val api: FilterApi) : FilterRepository {
    override suspend fun brand(): Either<Failure, List<SelectableDropDownItem>> {
        return apiCall(call = {
            api.brand()
        }, mapResponse = {
            it.data?.map { it.toUI() }.orEmpty()
        })
    }

    override suspend fun category(): Either<Failure, List<SelectableDropDownItem>> {
        return apiCall(call = {
            api.category()
        }, mapResponse = {
            it.data?.map { it.toUI() }.orEmpty()
        })
    }
}