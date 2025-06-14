package com.ansar.autoPartsApp.domain.useCase

import com.ansar.autoPartsApp.base.utils.BaseUseCase
import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope

internal class AuthUseCase(private val repository: AuthRepository) :
    BaseUseCase<AuthUseCase.Params, Unit>() {
    class Params(val login: String, val password: String)

    override suspend fun execute(params: Params, scope: CoroutineScope): Either<Failure, Unit> {
        return repository.auth(params.login, params.password)
    }
}