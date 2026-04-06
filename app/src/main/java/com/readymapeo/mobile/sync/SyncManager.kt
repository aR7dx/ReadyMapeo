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
    fun init(context: Context) {
        val workManager = WorkManager.getInstance(context)
        val networkConstraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        // One time synchronization
        val clubSyncNow = OneTimeWorkRequestBuilder<ClubSyncWorker>()
            .setConstraints(networkConstraint)
            .build()
        val raceSyncNow = OneTimeWorkRequestBuilder<RaceSyncWorker>()
            .setConstraints(networkConstraint)
            .build()
        val raidSyncNow = OneTimeWorkRequestBuilder<RaidSyncWorker>()
            .setConstraints(networkConstraint)
            .build()

        try {
            workManager.enqueueUniqueWork("club_sync_now", ExistingWorkPolicy.REPLACE, clubSyncNow)
        } catch (e: Exception) {}

        try {
            workManager.enqueueUniqueWork("race_sync_now", ExistingWorkPolicy.REPLACE, raceSyncNow)
        } catch (e: Exception) {}

        try {
            workManager.enqueueUniqueWork("raid_sync_now", ExistingWorkPolicy.REPLACE, raidSyncNow)
        } catch (e: Exception) {}

        // Periodic synchronization
        val clubSyncPeriodic = PeriodicWorkRequestBuilder<ClubSyncWorker>(5, TimeUnit.MINUTES)
            .setConstraints(networkConstraint)
            .build()
        val raceSyncPeriodic = PeriodicWorkRequestBuilder<RaceSyncWorker>(5, TimeUnit.MINUTES)
            .setConstraints(networkConstraint)
            .build()
        val raidSyncPeriodic = PeriodicWorkRequestBuilder<RaidSyncWorker>(5, TimeUnit.MINUTES)
            .setConstraints(networkConstraint)
            .build()

        // Calling periodic synchronization
        workManager.enqueueUniquePeriodicWork("club_sync_periodic", ExistingPeriodicWorkPolicy.KEEP, clubSyncPeriodic)
        workManager.enqueueUniquePeriodicWork("race_sync_periodic", ExistingPeriodicWorkPolicy.KEEP, raceSyncPeriodic)
        workManager.enqueueUniquePeriodicWork("raid_sync_periodic", ExistingPeriodicWorkPolicy.KEEP, raidSyncPeriodic)
    }
}
