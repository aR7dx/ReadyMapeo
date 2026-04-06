package com.readymapeo.mobile.sync

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.readymapeo.mobile.data.api.RaidApiService
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.manager.DataStoreProvider
import com.readymapeo.mobile.network.NetworkConnectivity
import org.json.JSONObject

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

             if (NetworkConnectivity.isOnline.value) {
                 syncUnsyncedRaids()
             }

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

     private suspend fun syncUnsyncedRaids() {
         try {
             val unsyncedRaids = raidDao.getUnsyncedRaids()

             if (unsyncedRaids.isEmpty()) {
                 return
             }

             for (raid in unsyncedRaids) {
                 try {
                     val body = JSONObject().apply {
                         put("raid_name", raid.raidName)
                         put("clu_id", raid.cluId)
                         put("raid_date_start", raid.raidDateStart)
                         put("raid_date_end", raid.raidDateEnd)
                         put("ins_start_date", raid.insStartDate)
                         put("ins_end_date", raid.insEndDate)
                         put("raid_description", raid.raidDescription)
                         put("raid_contact", raid.raidContact)
                         put("raid_street", raid.raidStreet)
                         put("raid_city", raid.raidCity)
                         put("raid_postal_code", raid.raidPostalCode)
                         put("raid_site_url", raid.raidSiteUrl)
                         put("raid_image", raid.raidImage)
                         put("adh_id", raid.adhId)
                         put("gestionnaire_raid_id", raid.adhId)
                     }

                     val response = com.readymapeo.mobile.network.ApiClient.post("/raids", body.toString())
                     val json = JSONObject(response)
                     val status = json.optString("status", "unknown")

                     if (status == "success") {
                         val raidData = json.optJSONObject("data")
                         if (raidData != null) {
                             val newRaidId = raidData.optInt("raid_id", raid.raidId)
                             if (newRaidId != raid.raidId) {
                                 raidDao.delete(raid)
                                 val updatedRaid = raid.copy(raidId = newRaidId, isSynced = true)
                                 raidDao.insert(updatedRaid)
                             } else {
                                 raidDao.markAsSynced(raid.raidId, System.currentTimeMillis())
                             }
                         } else {
                             raidDao.markAsSynced(raid.raidId, System.currentTimeMillis())
                         }
                     }
                 } catch (_: Exception) {}
             }
         } catch (_: Exception) {}
     }
}

