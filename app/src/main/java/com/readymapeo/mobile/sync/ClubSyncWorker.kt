package com.readymapeo.mobile.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.readymapeo.mobile.data.api.ClubApiService
import com.readymapeo.mobile.data.local.AppDatabase

class ClubSyncWorker(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {

    private val database = AppDatabase.getInstance(context)
    private val clubDao = database.clubDao()
    private val clubApiService = ClubApiService()

    override suspend fun doWork(): Result {
        return try {
            val clubs = clubApiService.getClubs()

            //clubDao.deleteAll()
            clubDao.insertAll(clubs)

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}