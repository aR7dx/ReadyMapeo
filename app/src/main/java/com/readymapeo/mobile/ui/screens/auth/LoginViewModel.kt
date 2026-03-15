package com.readymapeo.mobile.ui.screens.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.readymapeo.mobile.data.api.AuthApiService
import com.readymapeo.mobile.data.local.token.TokenManager
import com.readymapeo.mobile.data.repository.AuthRepository

class LoginViewModel(application: Application): AndroidViewModel(application) {


    suspend fun login (email: String, password: String): Result<String> {
        return AuthRepository.login(email, password)
    }

    suspend fun saveToken(token: String) {
        AuthRepository.saveToken(token)
    }

}