package com.readymapeo.mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
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

    @Query("""
        SELECT r.*, c.clubName from Raid r 
        JOIN Club c ON r.cluId = c.clubId
        WHERE 
            (:locationInputValue = '' 
                OR (:locationScope = 'Ville' AND LOWER(r.raidCity) LIKE '%' || LOWER(:locationInputValue) || '%')
                OR (:locationScope = 'Département' AND SUBSTR(r.raidPostalCode, 1, 2) = SUBSTR(:locationInputValue, 1, 2)))
            AND (:date IS NULL OR date(:date / 1000, 'unixepoch') BETWEEN DATE(r.raidDateStart) AND DATE(r.raidDateEnd))
        ORDER BY r.raidDateStart ASC
    """)
    fun getFilteredRaids(locationScope: String?, locationInputValue: String?, date: Long?): Flow<List<Raid>>

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