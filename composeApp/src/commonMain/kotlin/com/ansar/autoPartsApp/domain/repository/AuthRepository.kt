package com.ansar.autoPartsApp.domain.repository

import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure

interface AuthRepository {

    suspend fun auth(login: String, password: String): Either<Failure, Unit>
}