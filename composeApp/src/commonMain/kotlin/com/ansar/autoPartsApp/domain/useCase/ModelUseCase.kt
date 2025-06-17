package com.ansar.autoPartsApp.domain.useCase

import com.ansar.autoPartsApp.base.SelectableItem
import com.ansar.autoPartsApp.base.utils.BaseUseCase
import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.model.ModelUI
import com.ansar.autoPartsApp.domain.repository.FilterRepository
import kotlinx.coroutines.CoroutineScope

internal class ModelUseCase(private val repository: FilterRepository) :
    BaseUseCase<ModelUseCase.Params, List<SelectableItem<ModelUI>>>() {
    class Params

    override suspend fun execute(
        params: Params,
        scope: CoroutineScope
    ): Either<Failure, List<SelectableItem<ModelUI>>> {
        return repository.model()
    }
}