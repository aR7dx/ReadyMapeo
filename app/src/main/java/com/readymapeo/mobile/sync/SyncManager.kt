package com.readymapeo.mobile.sync

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object SyncManager {
    private const val RAID_SYNC_WORK_NAME = "raid_sync_periodic"
    private const val SYNC_INTERVAL_MINUTES = 5L

    fun initSyncWorker(context: Context) {
        val syncWorkRequest = PeriodicWorkRequestBuilder<RaidSyncWorker>(
            SYNC_INTERVAL_MINUTES,
            TimeUnit.MINUTES
        ).apply {
            // Ne synchroniser que si le réseau est disponible
            setConstraints(
                androidx.work.Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            // Backoff en cas d'erreur
            addTag("raid_sync")
            // setBackoffPolicy utilise des millisecondes initialement
            // Nous reviendrons à cela si nécessaire
        }.build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            RAID_SYNC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            syncWorkRequest
        )
    }

    fun stopSyncWorker(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(RAID_SYNC_WORK_NAME)
    }
}

