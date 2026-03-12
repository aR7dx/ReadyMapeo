package com.readymapeo.mobile.data.api

import com.readymapeo.mobile.data.local.dao.ClubDao
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.network.ApiClient
import com.readymapeo.mobile.utils.optBooleanOrNull
import com.readymapeo.mobile.utils.optStringOrNull
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class RaidApiService(private val clubDao: ClubDao) {

    suspend fun getRaids(): List<Raid> {
        return suspendCancellableCoroutine { continuation ->
            ApiClient.get("/raids") { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val dataArray = jsonObject.getJSONArray("data")

                    GlobalScope.launch {
                        val raids = mutableListOf<Raid>()
                        for (i in 0 until dataArray.length()) {
                            val raidJson = dataArray.getJSONObject(i)

                            val raid = parseRaidJson(raidJson)
                            if (raid != null) {
                                raids.add(raid)
                            }
                        }

                        continuation.resume(raids)
                    }
                }
                catch (e: Exception) {
                    continuation.resumeWithException(e)
                }
            }
        }
    }

    suspend fun getRaidById(raidId: Int): Raid? {
        return suspendCancellableCoroutine { continuation ->
            ApiClient.get("/raids/$raidId") { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val dataObject = jsonObject.getJSONObject("data")
                    val raidJson = dataObject.getJSONObject("raid")

                    GlobalScope.launch {
                        val raid = parseRaidJson(raidJson)
                        continuation.resume(raid)
                    }
                }
                catch (e: Exception) {
                    continuation.resumeWithException(e)
                }
            }
        }
    }

    suspend fun parseRaidJson(raidJson: JSONObject): Raid? {
        val racesArray = raidJson.optJSONArray("races")
        val cluId = raidJson.getInt("clu_id")

        // Verifie que le club existe
        if (clubDao.getById(cluId) == null) {
            return null
        }

        val raid = Raid(
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
            raidImage = raidJson.optStringOrNull("raid_image"),
            raidStreet = raidJson.getString("raid_street"),
            raidCity = raidJson.getString("raid_city"),
            raidPostalCode = raidJson.getString("raid_postal_code"),
            raidNumber = raidJson.getInt("raid_number"),
            createdAt = raidJson.getString("created_at"),
            updatedAt = raidJson.getString("updated_at"),
            racesCount = racesArray?.length() ?: 0,
            isOpen = raidJson.optBooleanOrNull("is_open"),
            isUpcoming = raidJson.optBooleanOrNull("is_upcoming"),
            isFinished = raidJson.optBooleanOrNull("is_finished"),
            lastSyncAt = System.currentTimeMillis(),
            isSynced = true
        )

        return raid
    }
}