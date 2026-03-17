package com.readymapeo.mobile.data.api

import com.readymapeo.mobile.data.local.entity.Club
import com.readymapeo.mobile.network.ApiClient
import com.readymapeo.mobile.utils.optBooleanOrNull
import com.readymapeo.mobile.utils.optIntOrNull
import com.readymapeo.mobile.utils.optStringOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

object ClubApiService {

    suspend fun getClubs(): List<Club> = withContext(Dispatchers.IO) {
        try {
            val response = ApiClient.get("/clubs")

            val jsonObject = JSONObject(response)
            val dataObject = jsonObject.getJSONObject("data")
            val dataArray = dataObject.getJSONArray("data")

            val clubs = mutableListOf<Club>()
            for (i in 0 until dataArray.length()) {
                val clubJson = dataArray.getJSONObject(i)

                val club = parseClubJson(clubJson)
                clubs.add(club)
            }

            clubs
        }
        catch (e: Exception) {
            throw e
        }
    }

    suspend fun getClubById(clubId: Int): Club = withContext(Dispatchers.IO) {
        try {
            val response = ApiClient.get("/clubs/$clubId")

            val json = JSONObject(response)
            val data = json.getJSONObject("data")

            val clubJson = data.getJSONObject("club")

            val club = parseClubJson(clubJson)
            club
        }
        catch (e: Exception) {
            throw e
        }
    }

    fun parseClubJson(clubJson: JSONObject): Club {
        return Club(
            clubId = clubJson.getInt("club_id"),
            clubName = clubJson.getString("club_name"),
            clubStreet = clubJson.getString("club_street"),
            clubCity = clubJson.getString("club_city"),
            clubPostalCode = clubJson.getString("club_postal_code"),
            ffsoId = clubJson.getString("ffso_id"),
            description = clubJson.optStringOrNull("description"),
            clubImage = clubJson.optStringOrNull("club_image"),
            isApproved = clubJson.optBooleanOrNull("is_approved"),
            approvedBy = clubJson.optIntOrNull("approved_by"),
            approvedAt = clubJson.optStringOrNull("approved_at"),
            createdBy = clubJson.optIntOrNull("created_by"),
            createdAt = clubJson.optStringOrNull("created_at"),
            updatedAt = clubJson.optStringOrNull("updated_at")
        )
    }
}