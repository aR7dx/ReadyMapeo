package com.readymapeo.mobile.ui.screens.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.readymapeo.mobile.data.AuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LogoutViewModel(application: Application): AndroidViewModel(application) {

    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut: StateFlow<Boolean?> = _isLoggedOut

    init {
        viewModelScope.launch {
            val loggedIn = AuthManager.isLoggedIn.value

            if (loggedIn == true) {
                AuthManager.logout()
            }

            _isLoggedOut.value = true
        }
    }
}

