package com.readymapeo.mobile.data.repository

import com.readymapeo.mobile.data.api.RaidApiService
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.network.NetworkConnectivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily

object RaidRepository {

    private lateinit var database: AppDatabase
    private var raidsStateFlow: StateFlow<List<Raid>>? = null

    fun setDatabase(appDatabase: AppDatabase) {
        if (!::database.isInitialized) {
            database = appDatabase
            RaidApiService.setClubDao(appDatabase.clubDao())
        }
    }

    private val raidDao get() = database.raidDao()

    @OptIn(DelicateCoroutinesApi::class)
    fun getAllRaids(): StateFlow<List<Raid>> {
        if (raidsStateFlow == null) {
            raidsStateFlow = flow {
                raidDao.getAll().collect { localRaids ->
                    if (localRaids.isEmpty() && NetworkConnectivity.isOnline.value) {
                        val raidsFromApi = RaidApiService.getRaids()
                        raidDao.insertAll(raidsFromApi)
                        emit(raidsFromApi)
                    } else {
                        emit(localRaids)
                    }
                }
            }.stateIn(GlobalScope, Lazily, emptyList())
        }
        return raidsStateFlow!!
    }

    suspend fun getRaidById(raidId: Int): Raid? {
        val raid = raidDao.getById(raidId)

        if (raid == null && NetworkConnectivity.isOnline.value){
            val raidFromApi = RaidApiService.getRaidById(raidId)

            if (raidFromApi != null) {
                raidDao.insert(raidFromApi)
                return raidFromApi
            }
        }

        return raid
    }

    fun getFilteredRaids(locationScope: String?, locationInputValue: String?, date: Long?): Flow<List<Raid>> {
        return raidDao.getFilteredRaids(locationScope, locationInputValue, date)
    }

    fun getLast3Raids(): Flow<List<Raid>> {
        return raidDao.getLast3Raids()
    }


}


