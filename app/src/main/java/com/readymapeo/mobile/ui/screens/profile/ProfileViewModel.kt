package com.readymapeo.mobile.ui.screens.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.readymapeo.mobile.data.local.entity.User
import com.readymapeo.mobile.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application): AndroidViewModel(application) {

    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user


    init {
        viewModelScope.launch {
            // verification du token
            val loggedIn = AuthRepository.isLoggedIn().first()
            _isLoggedIn.value = loggedIn
            
            // Si le user s'est connecté on charge ses infos
            if (loggedIn) {
                try {
                    AuthRepository.getUserInfo().collect { user ->
                        _user.value = user
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

