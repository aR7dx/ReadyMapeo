package com.readymapeo.mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.readymapeo.mobile.data.local.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Upsert
    fun insert(user: User)

    @Query("SELECT * FROM User LIMIT 1")
    fun getUser(): Flow<User>

    @Query("DELETE FROM User")
    fun delete()
}