package com.readymapeo.mobile.data.repository

import com.readymapeo.mobile.data.api.AuthApiService
import com.readymapeo.mobile.data.api.RaidApiService
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.entity.AuthenticatedUser
import com.readymapeo.mobile.manager.AuthManager
import com.readymapeo.mobile.manager.TokenManager
import com.readymapeo.mobile.manager.RolesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

object AuthRepository {

    private lateinit var database: AppDatabase

    fun setDatabase(appDatabase: AppDatabase) {
        if (!::database.isInitialized) {
            database = appDatabase
            RaidApiService.setClubDao(appDatabase.clubDao())
            RaidApiService.setRaceDao(appDatabase.raceDao())
        }
    }

    private val authenticatedUserDao get() = database.authenticatedUserDao()

    suspend fun login(email: String, password: String): Result<String> {
        return AuthApiService.login(email, password)
    }

    suspend fun logout() {
        TokenManager.clearAll()
        RolesManager.clearRoles()
        AuthManager.clearCache()
        authenticatedUserDao.delete()
    }

    suspend fun saveToken(token: String) {
        TokenManager.saveToken(token)
    }

    suspend fun getAuthenticatedUserInfo(): Flow<AuthenticatedUser> = withContext(Dispatchers.IO){
        authenticatedUserDao.delete()
        val authenticatedUser = AuthApiService.getUserInfo()
        authenticatedUserDao.insert(authenticatedUser)

        AuthManager.initializeUser(authenticatedUser)

        authenticatedUserDao.getAuthenticatedUser().flowOn(Dispatchers.IO)
    }

    fun isLoggedIn(): Flow<Boolean> {
        return TokenManager.getToken().map { token ->
            !token.isNullOrBlank()
        }
    }
}


