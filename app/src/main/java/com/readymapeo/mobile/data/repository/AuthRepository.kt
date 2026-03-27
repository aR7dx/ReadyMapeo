package com.readymapeo.mobile.data.repository

import com.readymapeo.mobile.data.api.AuthApiService
import com.readymapeo.mobile.data.api.RaidApiService
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.entity.User
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
        }
    }

    private val userDao get() = database.userDao()


    suspend fun login(email: String, password: String): Result<String> {
        return AuthApiService.login(email, password)
    }

    suspend fun logout() {
        TokenManager.clearAll()
        RolesManager.clearRoles()
        userDao.delete()
    }

    suspend fun saveToken(token: String) {
        TokenManager.saveToken(token)
    }

    suspend fun getUserInfo(): Flow<User> = withContext(Dispatchers.IO){
        userDao.delete()
        val user = AuthApiService.getUserInfo()
        userDao.insert(user)

        userDao.getUser().flowOn(Dispatchers.IO)
    }

    fun isLoggedIn(): Flow<Boolean> {
        return TokenManager.getToken().map { token ->
            !token.isNullOrBlank()
        }
    }
}


