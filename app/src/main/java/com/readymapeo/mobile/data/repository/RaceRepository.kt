package com.readymapeo.mobile.data.repository

import com.readymapeo.mobile.data.api.RaceApiService
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.entity.Race
import com.readymapeo.mobile.network.NetworkConnectivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext

object RaceRepository {
    private lateinit var database: AppDatabase
    private var racesStateFlow: StateFlow<List<Race>>? = null

    fun setDatabase(appDatabase: AppDatabase) {
        if (!::database.isInitialized) {
            database = appDatabase
            RaceApiService.setRaceDao(appDatabase.raceDao())
        }
    }

    private val raceDao get() = database.raceDao()

    @OptIn(DelicateCoroutinesApi::class)
    fun getAllRaces(): StateFlow<List<Race>> {
        if (racesStateFlow == null) {
            racesStateFlow = flow {
                raceDao.getAllRaces().collect { localRaces ->
                    if (localRaces.isEmpty() && NetworkConnectivity.isOnline.value) {
                        val racesFromApi = RaceApiService.getRaces()
                        raceDao.insertAll(racesFromApi)
                        emit(racesFromApi)
                    } else {
                        emit(localRaces)
                    }
                }
            }.stateIn(GlobalScope, Lazily, emptyList())
        }
        return racesStateFlow!!
    }

    fun getFilteredRaces(search: String): Flow<List<Race>> {
        return raceDao.getFilteredRaces(search)
    }

    suspend fun getRaceById(raceId: Int): Race? = withContext(Dispatchers.IO) {
        val race = raceDao.getRaceById(raceId)

        if (race == null && NetworkConnectivity.isOnline.value) {
            val raceFromApi = RaceApiService.getRaceById(raceId)

            raceDao.insert(raceFromApi)
            return@withContext raceFromApi
        }
        else {
            return@withContext race
        }
    }

    fun getRacesByRaidId(raidId: Int): Flow<List<Race>> {
        return flow {
            raceDao.getRacesByRaidId(raidId).collect { localRaces ->
                if (localRaces.isEmpty() && NetworkConnectivity.isOnline.value) {
                    try {
                        val racesFromApi = RaceApiService.getRacesByRaidId(raidId)
                        raceDao.insertAll(racesFromApi)
                        emit(racesFromApi)
                    } catch (_: Exception) {
                        emit(localRaces)
                    }
                } else {
                    emit(localRaces)
                }
            }
        }
    }
}