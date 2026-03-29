package com.readymapeo.mobile.data.repository

import com.readymapeo.mobile.data.api.UserApiService
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.entity.User
import kotlinx.coroutines.flow.Flow

object UserRepository {
    private lateinit var database: AppDatabase

    fun setDatabase(appDatabase: AppDatabase) {
        if (!::database.isInitialized) {
            database = appDatabase
            UserApiService.setUserDao(database.userDao())
        }
    }

    private val userDao get() = database.userDao()


    fun getMembersByClubId(clubId: Int): Flow<List<User>> {
        return userDao.getMembersByClubId(clubId)
    }

}