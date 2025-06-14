package com.ansar.autoPartsApp.domain.useCase

import com.ansar.autoPartsApp.base.SelectableDropDownItem
import com.ansar.autoPartsApp.base.SelectableItem
import com.ansar.autoPartsApp.base.utils.BaseUseCase
import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.model.BrandUI
import com.ansar.autoPartsApp.domain.repository.AuthRepository
import com.ansar.autoPartsApp.domain.repository.FilterRepository
import kotlinx.coroutines.CoroutineScope

internal class BrandUseCase(private val repository: FilterRepository) :
    BaseUseCase<BrandUseCase.Params, List<SelectableDropDownItem>>() {
    class Params()

    override suspend fun execute(
        params: Params,
        scope: CoroutineScope
    ): Either<Failure, List<SelectableDropDownItem>> {
        return repository.brand()
    }
}