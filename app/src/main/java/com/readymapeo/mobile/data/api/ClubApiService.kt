package com.readymapeo.mobile.data.api

import com.readymapeo.mobile.data.local.entity.Club
import com.readymapeo.mobile.network.ApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume

class ClubApiService {

    suspend fun getClubById(clubId: Int): Club? {
        return suspendCancellableCoroutine { continuation ->

            ApiClient.get("/clubs/$clubId") { response ->

                val jsonObject = JSONObject(response)
                val dataObject = jsonObject.getJSONObject("data")

                val clubJson = dataObject.getJSONObject("club")

                val club = Club(
                    clubId = clubJson.getInt("club_id"),
                    clubName = clubJson.getString("club_name")
                )

                continuation.resume(club)
            }
        }
    }
}