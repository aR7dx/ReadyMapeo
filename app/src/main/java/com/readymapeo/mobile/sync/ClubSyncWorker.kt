package com.readymapeo.mobile.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.readymapeo.mobile.data.api.ClubApiService
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.repository.ClubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClubSyncWorker(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {

    private val database = AppDatabase.getInstance(context)
    private val clubDao = database.clubDao()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            val clubs = ClubApiService.getClubs()
            clubDao.insertAll(clubs)

            val members = ClubApiService.getClubsMembers()
            ClubRepository.createClubsMembers(members)

            Result.success()
        }
        catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}