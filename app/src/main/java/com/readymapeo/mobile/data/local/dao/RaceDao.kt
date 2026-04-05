package com.readymapeo.mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.readymapeo.mobile.data.local.entity.Race
import kotlinx.coroutines.flow.Flow

@Dao
interface RaceDao {

    @Query("SELECT * FROM Race")
    fun getAllRaces(): Flow<List<Race>>

    @Query("SELECT * FROM Race WHERE raceId = :raceId")
    fun getRaceById(raceId: Int): Race?

    @Query("""
        SELECT * FROM Race
        WHERE raceRaidId = :raidId
        """)
    fun getRaceByRaidId(raidId: Int): Flow<List<Race>>

    @Query("""
        SELECT * FROM Race 
        WHERE LOWER(raceName) LIKE '%' || LOWER(:search) || '%'
        OR LOWER(raceRaidCity) LIKE '%' || LOWER(:search) || '%'
        OR LOWER(raceRaidName) LIKE '%' || LOWER(:search) || '%'
        OR LOWER(raceClubName) LIKE '%' || LOWER(:search) || '%'
        """)
    fun getFilteredRaces(search: String): Flow<List<Race>>

    @Upsert
    fun insert(race: Race)

    @Upsert
    fun insertAll(races: List<Race>)

    @Delete
    fun delete(race: Race)

    @Query("DELETE FROM Race")
    fun deleteAll()
}