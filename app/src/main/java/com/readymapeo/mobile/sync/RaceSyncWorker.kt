package com.readymapeo.mobile.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.readymapeo.mobile.data.api.RaceApiService
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.manager.DataStoreProvider
import com.readymapeo.mobile.network.NetworkConnectivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RaceSyncWorker(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {

    private val database = AppDatabase.getInstance(context)
    private val raceDao = database.raceDao()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            DataStoreProvider.init(applicationContext)

            val races = RaceApiService.getRaces()

            if (NetworkConnectivity.isOnline.value) {
                raceDao.deleteAll()
            }
            raceDao.insertAll(races)

            return@withContext Result.success()
        }
        catch (e: Exception) {
            e.printStackTrace()
            return@withContext Result.retry()
        }
    }
}