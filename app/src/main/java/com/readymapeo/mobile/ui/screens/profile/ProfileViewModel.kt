package com.readymapeo.mobile.ui.screens.profile

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.readymapeo.mobile.manager.AuthManager
import com.readymapeo.mobile.data.local.entity.AuthenticatedUser
import com.readymapeo.mobile.data.repository.AuthRepository
import com.readymapeo.mobile.manager.RolesManager
import com.readymapeo.mobile.utils.UserRole
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application): AndroidViewModel(application) {

    val roles = mutableStateOf(emptyList<String>())
    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    private val _user = MutableStateFlow<AuthenticatedUser?>(null)
    val user: StateFlow<AuthenticatedUser?> = _user


    init {
        viewModelScope.launch {
            AuthManager.isLoggedIn.collect { loggedIn ->
                _isLoggedIn.value = loggedIn
                
                if (loggedIn == true) {
                    try {
                        AuthRepository.getAuthenticatedUserInfo().collect { user ->
                            _user.value = user
                        }
                    } catch (e: Exception) {}
                } else {
                    _user.value = null
                }
            }
        }

        viewModelScope.launch {
            RolesManager.getRolesFlow()
                .distinctUntilChanged()
                .collect { rolesList ->
                    roles.value = rolesList
                }
        }
    }

    fun hasRole(role: UserRole): Boolean {
        return roles.value.contains(role.roleName)
    }
}
