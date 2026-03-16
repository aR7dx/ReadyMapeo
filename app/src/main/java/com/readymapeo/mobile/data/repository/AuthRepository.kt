package com.readymapeo.mobile.data.repository

import com.readymapeo.mobile.data.api.AuthApiService
import com.readymapeo.mobile.data.local.token.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object AuthRepository {

    suspend fun login(email: String, password: String): Result<String> {
        return AuthApiService.login(email, password)
    }

    suspend fun saveToken(token: String) {
        TokenManager.saveToken(token)
    }

    fun isLoggedIn(): Flow<Boolean> {
        return TokenManager.getToken().map { token ->
            !token.isNullOrBlank()
        }
    }
}