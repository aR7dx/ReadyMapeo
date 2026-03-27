package com.readymapeo.mobile.manager

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object RolesManager {

    private val ROLES_KEY = stringPreferencesKey("roles")
    private var cachedRoles: List<String> = emptyList()

    fun init() {
        // TODO
    }

    fun getCachedRoles(): List<String> {
        return cachedRoles
    }

    suspend fun saveRoles(roles: List<String>) {
        cachedRoles = roles
        DataStoreProvider.getDataStore().edit { preferences ->
            preferences[ROLES_KEY] = roles.joinToString(",")
        }
    }

    fun getRolesFlow(): Flow<List<String>> {
        return DataStoreProvider.getDataStore().data.map { preferences ->
            val rolesStr = preferences[ROLES_KEY] ?: ""
            if (rolesStr.isEmpty()) emptyList() else rolesStr.split(",")
        }
    }

    suspend fun clearRoles() {
        cachedRoles = emptyList()
        DataStoreProvider.getDataStore().edit { preferences ->
            preferences.remove(ROLES_KEY)
        }
    }
}