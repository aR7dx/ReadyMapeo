package com.readymapeo.mobile.data.repository

import com.readymapeo.mobile.data.api.ClubApiService
import com.readymapeo.mobile.data.local.AppDatabase

object ClubMemberRepository {

    private lateinit var database : AppDatabase

    fun setDatabase(appDatabase: AppDatabase) {
        if (!::database.isInitialized) {
            database = appDatabase
        }
    }

}