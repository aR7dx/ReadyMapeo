package com.readymapeo.mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.readymapeo.mobile.data.local.entity.Club
import kotlinx.coroutines.flow.Flow

@Dao
interface ClubDao {

    @Query("SELECT * from Club")
    fun getAll(): Flow<List<Club>>

    @Query("SELECT * from Club WHERE clubId = :id")
    fun getById(id: Int): Club?

    @Query("""
        SELECT * from Club 
        WHERE LOWER(clubName) LIKE '%' || LOWER(:search) || '%'
        OR LOWER(clubCity) LIKE '%' || LOWER(:search) || '%'
        OR clubPostalCode LIKE '%' || :search || '%'
    """)
    fun getFilteredClubs(search: String): Flow<List<Club>>

    @Upsert
    fun insert(club: Club)

    @Upsert
    fun insertAll(clubs: List<Club>)

    @Delete
    fun delete(club: Club)

    @Query("UPDATE Club SET isSynced = 1, lastSyncAt = :timestamp WHERE clubId = :id")
    fun markAsSynced(id: Int, timestamp: Long)

    @Query("UPDATE Club SET isSynced = 1, lastSyncAt = :timestamp")
    fun markAllAsSynced(timestamp: Long)

    @Query("DELETE FROM Club")
    fun deleteAll()

    @Query("SELECT * FROM Club WHERE createdBy = :managerId")
    fun getClubsOfAManager(managerId: Int): Flow<List<Club>>
}