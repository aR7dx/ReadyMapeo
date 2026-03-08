package com.readymapeo.mobile.data.api

import com.readymapeo.mobile.data.model.Raid
import com.readymapeo.mobile.data.model.RaidClub
import com.readymapeo.mobile.data.model.RaidRegistrationPeriod
import com.readymapeo.mobile.network.ApiClient
import org.json.JSONObject

class ReadyMapeoApi {
    fun getRaids(callback: (List<Raid>) -> Unit) {
        ApiClient.get("/raids") { response ->

            val jsonObject = JSONObject(response)
            val dataArray = jsonObject.getJSONArray("data")

            val raids = mutableListOf<Raid>()

            for (i in 0 until dataArray.length()) {
                val raidJson = dataArray.getJSONObject(i)
                val clubJson = raidJson.optJSONObject("club")
                val registrationJson = raidJson.optJSONObject("registration_period")

                raids.add(
                    Raid(
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
                        racesCount = raidJson.getInt("races_count"),
                        club = RaidClub(
                            clubId = clubJson?.getInt("club_id") ?: 0,
                            clubName = clubJson?.getString("club_name") ?: "",
                        ),
                        registrationPeriod = RaidRegistrationPeriod(
                            insId = registrationJson?.getInt("ins_id") ?: 0,
                            insStartDate = registrationJson?.getString("ins_start_date") ?: "",
                            insEndDate = registrationJson?.getString("ins_end_date") ?: "",
                        ),
                    )
                )
            }

            callback(raids)
        }
    }
}