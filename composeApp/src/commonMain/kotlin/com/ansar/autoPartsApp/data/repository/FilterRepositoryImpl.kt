package com.ansar.autoPartsApp.data.repository

import com.ansar.autoPartsApp.base.SelectableDropDownItem
import com.ansar.autoPartsApp.base.SelectableItem
import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.base.utils.apiCall
import com.ansar.autoPartsApp.data.api.FilterApi
import com.ansar.autoPartsApp.data.mapper.toUI
import com.ansar.autoPartsApp.domain.model.ModelUI
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

    override suspend fun model(): Either<Failure, List<SelectableItem<ModelUI>>> {
        return apiCall(call = {
            api.model()
        }, mapResponse = { data ->
            data.data?.map {
                SelectableItem(
                    ModelUI(it.id ?: 0, it.title.orEmpty()),
                    false
                )
            }.orEmpty()
        })
    }
}