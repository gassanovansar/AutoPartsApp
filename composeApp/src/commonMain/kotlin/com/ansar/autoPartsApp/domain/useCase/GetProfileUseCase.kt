package com.ansar.autoPartsApp.domain.useCase

import com.ansar.autoPartsApp.base.utils.BaseUseCase
import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.model.ProfileUI
import com.ansar.autoPartsApp.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope

internal class GetProfileUseCase(private val repository: AuthRepository) :
    BaseUseCase<GetProfileUseCase.Params, ProfileUI>() {
    class Params()

    override suspend fun execute(params: Params, scope: CoroutineScope): Either<Failure, ProfileUI> {
        return repository.getProfile()
    }
}