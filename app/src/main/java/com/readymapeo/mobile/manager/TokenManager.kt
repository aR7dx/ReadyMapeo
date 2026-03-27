package com.readymapeo.mobile.manager

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object TokenManager {

    private val TOKEN_KEY = stringPreferencesKey("api_token")

    fun init() {
        //verifyToken()
    }

    suspend fun saveToken(token: String) {
        DataStoreProvider.getDataStore().edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    fun getToken(): Flow<String?> {
        return DataStoreProvider.getDataStore().data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }
/*
    suspend fun verifyToken() {

    }*/

    suspend fun deleteToken() {
        DataStoreProvider.getDataStore().edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    suspend fun clearAll() {
        DataStoreProvider.getDataStore().edit { preferences ->
            preferences.clear()
        }
    }
}