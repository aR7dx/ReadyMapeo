package com.readymapeo.mobile.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "prefs")

object DataStoreProvider {
    private var _dataStore: DataStore<Preferences>? = null

    fun init(context: Context) {
        if (_dataStore == null) {
            _dataStore = context.dataStore
        }
    }

    fun getDataStore(): DataStore<Preferences> {
        return _dataStore ?: throw IllegalStateException("DataStore not initialized")
    }
}