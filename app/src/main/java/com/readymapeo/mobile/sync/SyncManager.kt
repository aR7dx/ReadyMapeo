package com.readymapeo.mobile.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object SyncManager {
    fun initSyncWorker(context: Context) {
        val workManager = WorkManager.getInstance(context)
        val networkConstraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val clubSyncNow = OneTimeWorkRequestBuilder<ClubSyncWorker>()
            .setConstraints(networkConstraint)
            .build()
        val raidSyncNow = OneTimeWorkRequestBuilder<RaidSyncWorker>()
            .setConstraints(networkConstraint)
            .build()

        workManager.beginUniqueWork("club_sync_now", ExistingWorkPolicy.KEEP, clubSyncNow)
            .then(raidSyncNow)
            .enqueue()

        val clubSyncPeriodic = PeriodicWorkRequestBuilder<ClubSyncWorker>(5, TimeUnit.MINUTES)
            .setConstraints(networkConstraint)
            .build()
        val raidSyncPeriodic = PeriodicWorkRequestBuilder<RaidSyncWorker>(5, TimeUnit.MINUTES)
            .setConstraints(networkConstraint)
            .build()

        workManager.enqueueUniquePeriodicWork("club_sync_periodic", ExistingPeriodicWorkPolicy.KEEP, clubSyncPeriodic)
        workManager.enqueueUniquePeriodicWork("raid_sync_periodic", ExistingPeriodicWorkPolicy.KEEP, raidSyncPeriodic)
    }
}
