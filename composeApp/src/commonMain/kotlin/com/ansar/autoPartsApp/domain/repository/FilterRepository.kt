package com.ansar.autoPartsApp.domain.repository

import com.ansar.autoPartsApp.base.SelectableDropDownItem
import com.ansar.autoPartsApp.base.SelectableItem
import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.model.ModelUI

interface FilterRepository {

    suspend fun brand(): Either<Failure, List<SelectableDropDownItem>>
    suspend fun category(): Either<Failure, List<SelectableDropDownItem>>
    suspend fun model(): Either<Failure, List<SelectableItem<ModelUI>>>
}