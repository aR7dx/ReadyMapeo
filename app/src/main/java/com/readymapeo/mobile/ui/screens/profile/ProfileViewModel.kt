package com.readymapeo.mobile.ui.screens.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.readymapeo.mobile.data.AuthManager
import com.readymapeo.mobile.data.local.entity.User
import com.readymapeo.mobile.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application): AndroidViewModel(application) {

    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user


    init {
        viewModelScope.launch {
            AuthManager.isLoggedIn.collect { loggedIn ->
                _isLoggedIn.value = loggedIn
                
                if (loggedIn == true) {
                    try {
                        AuthRepository.getUserInfo().collect { user ->
                            _user.value = user
                        }
                    } catch (e: Exception) {}
                } else {
                    _user.value = null
                }
            }
        }
    }
}
