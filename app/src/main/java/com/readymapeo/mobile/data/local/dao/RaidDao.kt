package com.readymapeo.mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.readymapeo.mobile.data.local.entity.Raid
import kotlinx.coroutines.flow.Flow

@Dao
interface RaidDao {

    @Query("SELECT * from Raid")
    fun getAll(): Flow<List<Raid>>

    @Insert suspend fun insert(raid: Raid)

    @Delete suspend fun delete(raid: Raid)
}