package com.readymapeo.mobile.data.api

import com.readymapeo.mobile.data.local.dao.ClubDao
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.network.ApiClient
import com.readymapeo.mobile.utils.optBooleanOrNull
import com.readymapeo.mobile.utils.optStringOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

object RaidApiService {

    private var clubDao: ClubDao? = null

    fun setClubDao(dao: ClubDao) {
        if (clubDao == null) {
            clubDao = dao
        }
    }

    suspend fun getRaids(): List<Raid> = withContext(Dispatchers.IO) {
        try {
            val response = ApiClient.get("/raids")
            val json = JSONObject(response)
            val data = json.getJSONArray("data")

            val raids = mutableListOf<Raid>()
            for (i in 0 until data.length()) {
                val raidJson = data.getJSONObject(i)

                val raid = parseRaidJson(raidJson)
                if (raid != null) {
                    raids.add(raid)
                }
            }

            return@withContext raids
        }
        catch (e: Exception) {
           throw e
        }
    }

    suspend fun getRaidById(raidId: Int): Raid? = withContext(Dispatchers.IO) {
        try {
            val response = ApiClient.get("/raids/$raidId")
            val json = JSONObject(response)
            val data = json.getJSONObject("data")

            val raidJson = data.getJSONObject("raid")

            val raid = parseRaidJson(raidJson)
            raid
        }
        catch (e: Exception) {
            throw e
        }
    }

    fun parseRaidJson(raidJson: JSONObject): Raid? {
        val racesArray = raidJson.optJSONArray("races")
        val cluId = raidJson.getInt("clu_id")
        val registrationJson = raidJson.getJSONObject("registration_period")

        // Vérifie que le club existe
        val club = clubDao?.getById(cluId) ?: return null


        return Raid(
            raidId = raidJson.getInt("raid_id"),
            raidName = raidJson.getString("raid_name"),
            raidDescription = raidJson.getString("raid_description"),
            adhId = raidJson.getInt("adh_id"),
            cluId = raidJson.getInt("clu_id"),
            insId = raidJson.getInt("ins_id"),
            raidDateStart = raidJson.getString("raid_date_start"),
            raidDateEnd = raidJson.getString("raid_date_end"),
            raidContact = raidJson.getString("raid_contact"),
            raidSiteUrl = raidJson.optStringOrNull("raid_site_url"),
            raidImage = raidJson.optStringOrNull("raid_image"),
            raidStreet = raidJson.getString("raid_street"),
            raidCity = raidJson.getString("raid_city"),
            raidPostalCode = raidJson.getString("raid_postal_code"),
            raidNumber = raidJson.getInt("raid_number"),
            createdAt = raidJson.getString("created_at"),
            updatedAt = raidJson.getString("updated_at"),
            racesCount = racesArray?.length() ?: 0,
            clubName = club.clubName,
            insStartDate = registrationJson.getString("ins_start_date"),
            insEndDate = registrationJson.getString("ins_end_date"),
            isOpen = raidJson.optBooleanOrNull("is_open"),
            isUpcoming = raidJson.optBooleanOrNull("is_upcoming"),
            isFinished = raidJson.optBooleanOrNull("is_finished"),
            lastSyncAt = System.currentTimeMillis(),
            isSynced = true
        )
    }
}