package com.readymapeo.mobile.data.repository

import com.readymapeo.mobile.data.api.AuthApiService
import com.readymapeo.mobile.data.local.token.TokenManager
import kotlinx.coroutines.flow.first

object AuthRepository {

    suspend fun login(email: String, password: String): Result<String> {
        return AuthApiService.login(email, password)
    }

    suspend fun saveToken(token: String) {
        TokenManager.saveToken(token)
    }

    suspend fun isLoggedIn(): Boolean {
        val token = TokenManager.getToken().first()
        return !token.isNullOrBlank()
    }
}