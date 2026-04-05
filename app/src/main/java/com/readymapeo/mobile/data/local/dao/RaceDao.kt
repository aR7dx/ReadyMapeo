package com.readymapeo.mobile.data.local.dao

import androidx.room.Dao
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

    @Upsert
    fun insert(race: Race)

    @Upsert
    fun insertAll(races: List<Race>)
}