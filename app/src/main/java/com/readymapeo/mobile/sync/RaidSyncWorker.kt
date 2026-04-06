package com.readymapeo.mobile.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.readymapeo.mobile.data.api.RaidApiService
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.manager.DataStoreProvider
import com.readymapeo.mobile.network.NetworkConnectivity

class RaidSyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    private val database = AppDatabase.getInstance(context)
    private val raidDao = database.raidDao()
    private val clubDao = database.clubDao()
    private val raceDao = database.raceDao()

    init {
        RaidApiService.setClubDao(clubDao)
        RaidApiService.setRaceDao(raceDao)
    }

    override suspend fun doWork(): Result {
        return try {
            DataStoreProvider.init(applicationContext)

            val raids = RaidApiService.getRaids()

            if (NetworkConnectivity.isOnline.value) {
                raidDao.deleteAll()
            }
            raidDao.insertAll(raids)

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}

