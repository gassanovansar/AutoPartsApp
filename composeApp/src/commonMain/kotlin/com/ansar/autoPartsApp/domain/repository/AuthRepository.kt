package com.ansar.autoPartsApp.domain.repository

import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.model.ProfileUI

interface AuthRepository {

    suspend fun auth(login: String, password: String): Either<Failure, Unit>
    suspend fun getProfile(): Either<Failure, ProfileUI>
}