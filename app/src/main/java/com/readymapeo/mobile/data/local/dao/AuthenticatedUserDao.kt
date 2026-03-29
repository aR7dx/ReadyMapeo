package com.readymapeo.mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.readymapeo.mobile.data.local.entity.AuthenticatedUser
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthenticatedUserDao {

    @Upsert
    suspend fun insert(authenticatedUser: AuthenticatedUser)


    @Query("SELECT * FROM AuthenticatedUser LIMIT 1")
    fun getAuthenticatedUser(): Flow<AuthenticatedUser>

    @Query("DELETE FROM AuthenticatedUser")
    suspend fun delete()
}