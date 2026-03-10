package com.readymapeo.mobile.data.api

import com.readymapeo.mobile.data.local.entity.Club
import com.readymapeo.mobile.network.ApiClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ClubApiService {

    suspend fun getClubs(): List<Club> {
        return suspendCancellableCoroutine { continuation ->
            ApiClient.get("/clubs") { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val dataObject = jsonObject.getJSONObject("data")
                    val dataArray = dataObject.getJSONArray("data")

                    GlobalScope.launch {
                        val clubs = mutableListOf<Club>()
                        for (i in 0 until dataArray.length()) {
                            val clubJson = dataArray.getJSONObject(i)

                            val club = parseClubJson(clubJson)
                            if (club != null) {
                                clubs.add(club)
                            }
                        }

                        continuation.resume(clubs)
                    }
                }
                catch (e: Exception) {
                    continuation.resumeWithException(e)
                }
            }
        }
    }

    suspend fun getClubById(clubId: Int): Club? {
        return suspendCancellableCoroutine { continuation ->

            ApiClient.get("/clubs/$clubId") { response ->
                try {

                    val jsonObject = JSONObject(response)
                    val dataObject = jsonObject.getJSONObject("data")

                    val clubJson = dataObject.getJSONObject("club")

                    GlobalScope.launch {
                        val club = parseClubJson(clubJson)
                        continuation.resume(club)
                    }
                }
                catch (e: Exception) {
                    continuation.resumeWithException(e)
                }
            }
        }
    }

    suspend fun parseClubJson(clubJson: JSONObject): Club? {
        val club = Club(
            clubId = clubJson.getInt("club_id"),
            clubName = clubJson.getString("club_name")
        )

        return club
    }
}