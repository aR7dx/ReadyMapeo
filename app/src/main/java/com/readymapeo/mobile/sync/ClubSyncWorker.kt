package com.readymapeo.mobile.sync

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.readymapeo.mobile.data.api.ClubApiService
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.repository.ClubRepository
import com.readymapeo.mobile.manager.DataStoreProvider
import com.readymapeo.mobile.network.NetworkConnectivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClubSyncWorker(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {

    private val database = AppDatabase.getInstance(context)
    private val clubDao = database.clubDao()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
         try {
            DataStoreProvider.init(applicationContext)
            ClubRepository.setDatabase(database)

            val clubs = ClubApiService.getClubs()

            if (NetworkConnectivity.isOnline.value) {
                clubDao.deleteAll()
            }
            clubDao.insertAll(clubs)

            if (NetworkConnectivity.isOnline.value) {
                val members = ClubApiService.getClubsMembers()
                ClubRepository.createClubsMembers(members)
            }

            return@withContext Result.success()
        }
        catch (e: Exception) {
            e.printStackTrace()
            return@withContext Result.retry()
        }
    }
}