package com.readymapeo.mobile.data.api

import com.readymapeo.mobile.data.local.dao.RaceDao
import com.readymapeo.mobile.data.local.entity.Race
import com.readymapeo.mobile.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

object RaceApiService {

    private var raceDao: RaceDao? = null

    fun setRaceDao(dao: RaceDao) {
        if (raceDao == null) {
            raceDao = dao
        }
    }

    suspend fun getRaces(): List<Race> = withContext(Dispatchers.IO) {
        try {
            val response = ApiClient.get("/races")
            val json = JSONObject(response)
            val data = json.getJSONArray("data")

            val races = mutableListOf<Race>()
            for (i in 0 until data.length()) {
                val raceJson = data.getJSONObject(i)

                val race = parseRaceJson(raceJson)
                races.add(race)
            }

            return@withContext races
        }
        catch(e: Exception) {
            throw e
        }
    }

    fun parseRaceJson(raceJson: JSONObject): Race {
        val raidJson = raceJson.getJSONObject("raid")
        val clubJson = raidJson.getJSONObject("club")
        val organizerJson = raceJson.getJSONObject("organizer")

        return Race(
            raceId = raceJson.getInt("race_id"),
            raceName = raceJson.getString("race_name"),
            raceDescription = raceJson.getString("race_description"),
            raceDateStart = raceJson.getString("race_date_start"),
            raceDateEnd = raceJson.getString("race_date_end"),
            raceDifficulty = raceJson.getString("race_difficulty"),
            raceDurationMinutes = raceJson.getInt("race_duration_minutes"),
            raceType = raceJson.getString("type"),
            raceImageUrl = raceJson.getString("image_url"),
            racePriceMajor = raceJson.getInt("price_major"),
            racePriceMinor = raceJson.getInt("price_minor"),
            raceIsOpen = raceJson.getBoolean("is_open"),
            raceRaidId = raidJson.getInt("id"),
            raceRaidName = raidJson.getString("name"),
            raceRaidCity = raidJson.getString("city"),
            raceClubId = clubJson.getInt("id"),
            raceClubName = clubJson.getString("name"),
            raceOrganizerName = organizerJson.getString("name"),
            raceAgeCategories = raceJson.optJSONArray("age_categories")?.toString()

        )
    }

}