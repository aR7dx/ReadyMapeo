package com.readymapeo.mobile.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.readymapeo.mobile.data.api.RaidApiService
import com.readymapeo.mobile.data.local.AppDatabase

class RaidSyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    private val database = AppDatabase.getInstance(context)
    private val raidDao = database.raidDao()
    private val clubDao = database.clubDao()

    init {
        RaidApiService.setClubDao(clubDao)
    }

    override suspend fun doWork(): Result {
        return try {
            val raids = RaidApiService.getRaids()

            //raidDao.deleteAll()
            raidDao.insertAll(raids)

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}

