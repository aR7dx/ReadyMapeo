package com.readymapeo.mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.readymapeo.mobile.data.local.entity.Raid
import kotlinx.coroutines.flow.Flow

@Dao
interface RaidDao {

    @Query("SELECT r.*, c.clubName  from Raid r JOIN Club c ON r.cluId = c.clubId")
    fun getAll(): Flow<List<Raid>>

    @Query("SELECT * from Raid WHERE raidId = :id")
    suspend fun getById(id: Int): Raid?

    @Upsert
    suspend fun insert(raid: Raid)

    @Upsert
    suspend fun insertAll(raids: List<Raid>)

    @Delete
    suspend fun delete(raid: Raid)

    @Query("UPDATE Raid SET isSynced = 1, lastSyncAt = :timestamp WHERE raidId = :id")
    suspend fun markAsSynced(id: Int, timestamp: Long)

    @Query("UPDATE Raid SET isSynced = 1, lastSyncAt = :timestamp")
    suspend fun markAllAsSynced(timestamp: Long)

    @Query("DELETE FROM Raid")
    suspend fun deleteAll()
}