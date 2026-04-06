package com.readymapeo.mobile.manager

import com.readymapeo.mobile.data.local.entity.AuthenticatedUser
import com.readymapeo.mobile.data.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Gestionnaire de l'état d'authentification de l'utilisateur
 */
object AuthManager {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private var authenticatedUser: AuthenticatedUser? = null
    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn.asStateFlow()

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()


    /**
     * Initialise le gestionnaire d'authentification
     */
    fun init() {
        if (_isInitialized.value) return

        scope.launch {
            TokenManager.getToken().collect { token ->
                _isLoggedIn.value = !token.isNullOrBlank()
            }
        }

        _isInitialized.value = true
    }

    /**
     * Initialise le cache avec les infos de l'utilisateur authentifié
     */
    fun initializeUser(user: AuthenticatedUser) {
        AuthManager.authenticatedUser = user
    }

    /**
     * Récupère l'utilisateur authentifié en cache
     */
    fun getAuthenticatedUser(): AuthenticatedUser? = AuthManager.authenticatedUser

    /**
     * Vide le cache lors de la déconnexion
     */
    fun clearCache() {
        AuthManager.authenticatedUser = null
    }

    /**
     * Effectue une connexion au compte de l'utilisateur
     */
    suspend fun login(email: String, password: String): Result<String> {
        return AuthRepository.login(email, password).also { result ->
            result.onSuccess { token ->
                AuthRepository.saveToken(token)
            }
        }
    }

    /**
     * Effectue une déconnexion du compte
     */
    suspend fun logout() {
        AuthRepository.logout()
    }

    suspend fun isLoggedIn(): Flow<Boolean> {
        return AuthRepository.isLoggedIn()
    }
}