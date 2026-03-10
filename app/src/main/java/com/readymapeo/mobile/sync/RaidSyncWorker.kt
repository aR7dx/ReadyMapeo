package com.readymapeo.mobile.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.network.ApiClient
import org.json.JSONObject

class RaidSyncWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    private val database = AppDatabase.getInstance(context)
    private val raidDao = database.raidDao()

    override suspend fun doWork(): Result {
        return try {
            // Récupérer les raids depuis l'API
            val response = ApiClient.getSync("/raids")
            val jsonObject = JSONObject(response)
            val dataArray = jsonObject.getJSONArray("data")

            val raids = mutableListOf<com.readymapeo.mobile.data.local.entity.Raid>()
            val currentTime = System.currentTimeMillis()

            for (i in 0 until dataArray.length()) {
                val raidJson = dataArray.getJSONObject(i)
                val clubJson = raidJson.optJSONObject("club")

                raids.add(
                    com.readymapeo.mobile.data.local.entity.Raid(
                        raidId = raidJson.getInt("raid_id"),
                        raidName = raidJson.getString("raid_name"),
                        raidDescription = raidJson.getString("raid_description"),
                        adhId = raidJson.getInt("adh_id"),
                        cluId = raidJson.getInt("clu_id"),
                        insId = raidJson.getInt("ins_id"),
                        raidDateStart = raidJson.getString("raid_date_start"),
                        raidDateEnd = raidJson.getString("raid_date_end"),
                        raidContact = raidJson.getString("raid_contact"),
                        raidSiteUrl = raidJson.getString("raid_site_url"),
                        raidImage = raidJson.getString("raid_image"),
                        raidStreet = raidJson.getString("raid_street"),
                        raidCity = raidJson.getString("raid_city"),
                        raidPostalCode = raidJson.getString("raid_postal_code"),
                        raidNumber = raidJson.getInt("raid_number"),
                        createdAt = raidJson.getString("created_at"),
                        updatedAt = raidJson.getString("updated_at"),
                        //clubName = clubJson?.getString("club_name") ?: "",
                        racesCount = raidJson.optInt("races_count"),
                        isOpen = raidJson.optBoolean("is_open"),
                        isUpcoming = raidJson.optBoolean("is_upcoming"),
                        isFinished = raidJson.optBoolean("is_finished"),
                        lastSyncAt = currentTime,
                        isSynced = true
                    )
                )
            }

            raidDao.deleteAll()
            raidDao.insertAll(raids)

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}

