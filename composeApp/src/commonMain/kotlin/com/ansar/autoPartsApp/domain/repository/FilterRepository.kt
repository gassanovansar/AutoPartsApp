package com.ansar.autoPartsApp.domain.repository

import com.ansar.autoPartsApp.base.SelectableDropDownItem
import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure

interface FilterRepository {

    suspend fun brand(): Either<Failure, List<SelectableDropDownItem>>
    suspend fun category(): Either<Failure, List<SelectableDropDownItem>>
}