package com.ansar.autoPartsApp.data.repository

import com.ansar.autoPartsApp.base.utils.Either
import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.base.utils.apiCall
import com.ansar.autoPartsApp.data.api.AuthApi
import com.ansar.autoPartsApp.domain.manager.SessionManager
import com.ansar.autoPartsApp.domain.repository.AuthRepository

class AuthRepositoryImpl(private val api: AuthApi, private val sessionManager: SessionManager) :
    AuthRepository {
    override suspend fun auth(login: String, password: String): Either<Failure, Unit> {
        return apiCall(call = {
            api.auth(
                username = login,
                password = password
            )
        }, mapResponse = {
            sessionManager.auth(it.token.orEmpty(), "")
        })
    }
}