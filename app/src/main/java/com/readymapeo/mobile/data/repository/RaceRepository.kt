package com.readymapeo.mobile.data.repository

import com.readymapeo.mobile.data.api.RaceApiService
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.entity.Race
import com.readymapeo.mobile.network.NetworkConnectivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

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
}