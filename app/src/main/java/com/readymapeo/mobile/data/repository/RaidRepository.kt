package com.readymapeo.mobile.data.repository

import com.readymapeo.mobile.data.api.RaidApiService
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.network.NetworkConnectivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RaidRepository(private val database: AppDatabase) {

    private val raidApiService = RaidApiService(database.clubDao())
    private val raidDao = database.raidDao()

    fun getAllRaids(): Flow<List<Raid>> = flow {

        raidDao.getAll().collect { localRaids ->
            println("${localRaids.size} raids trouvé en bdd")

            if (localRaids.isNotEmpty()) {
                emit(localRaids)
            }
            else if (NetworkConnectivity.isOnline.value) {
                val raidsFromApi = raidApiService.getRaids()

                raidDao.insertAll(raidsFromApi)
                emit(raidsFromApi)
            }
            else {
                emit(localRaids)
            }
        }
    }

    suspend fun getRaidById(raidId: Int): Raid? {
        val raid = raidDao.getById(raidId)

        if (raid != null) {
            return raid
        }
        else if (NetworkConnectivity.isOnline.value){
            val raidFromApi = raidApiService.getRaidById(raidId)

            raidDao.insert(raidFromApi!!)
            return raidFromApi
        }
        else {
            return raid
        }
    }


}


