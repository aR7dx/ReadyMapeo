package com.readymapeo.mobile.data.api

import com.readymapeo.mobile.data.local.dao.ClubMemberDao
import com.readymapeo.mobile.data.local.entity.Club
import com.readymapeo.mobile.data.local.entity.ClubMember
import com.readymapeo.mobile.data.local.entity.User
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
            val json = JSONObject(response)

            val dataObject = json.getJSONObject("data")
            val dataArray = dataObject.getJSONArray("data")

            val clubs = mutableListOf<Club>()

            for (i in 0 until dataArray.length()) {
                val clubJson = dataArray.getJSONObject(i)
                val creatorJson = clubJson.optJSONObject("creator")

                val club = parseClubJson(null, clubJson, creatorJson!!)
                clubs.add(club)
            }

            return@withContext clubs
        }
        catch (e: Exception) {
            throw e
        }
    }

    suspend fun getClubsMembers(): List<Pair<User, Int>> = withContext(Dispatchers.IO) {
        try {
            val response = ApiClient.get("/clubs")
            val json = JSONObject(response)

            val dataObject = json.getJSONObject("data")
            val dataArray = dataObject.getJSONArray("data")

            val members = mutableListOf<Pair<User, Int>>()

            for (i in 0 until dataArray.length()) {
                val clubJson = dataArray.getJSONObject(i)
                val membersArray = clubJson.getJSONArray("members")

                for (j in 0 until membersArray.length()) {
                    val clubId = clubJson.getInt("club_id")

                    val memberJson = membersArray.getJSONObject(j)
                    val member = parseClubMemberJson(clubId, memberJson)

                    members.add(Pair(member, clubId))
                }
            }

            return@withContext members
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
            val creatorJson = clubJson.optJSONObject("creator")

            val club = parseClubJson(data, clubJson, creatorJson!!)
            return@withContext club
        }
        catch (e: Exception) {
            throw e
        }
    }

    fun parseClubJson(
        dataJson: JSONObject? = null,
        clubJson: JSONObject,
        creatorJson: JSONObject
    ): Club {

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
            creatorName = creatorJson.optStringOrNull("name"),
            createdBy = clubJson.optIntOrNull("created_by"),
            createdAt = clubJson.optStringOrNull("created_at"),
            updatedAt = clubJson.optStringOrNull("updated_at"),
            isMember = dataJson?.optBooleanOrNull("isMember"),
            isManager = dataJson?.optBooleanOrNull("isManager"),
            membershipStatus = dataJson?.optBooleanOrNull("membershipStatus"),
        )
    }

    fun parseClubMemberJson(clubId: Int, memberJson: JSONObject): User {
        return User(
            id = memberJson.getInt("id"),
            name = memberJson.getString("name"),
            lastName = memberJson.getString("last_name"),
            firstName = memberJson.getString("first_name"),
            description = memberJson.getString("description"),
            email = memberJson.getString("email"),
            birthDate = memberJson.getString("birth_date"),
            address = memberJson.getString("address"),
            phone = memberJson.getString("phone"),
            active = memberJson.getInt("active"),
            profilePhotoUrl = memberJson.getString("profile_photo_url"),
            profilePhotoPath = memberJson.getString("profile_photo_path"),
            createdAt = memberJson.getString("created_at"),
            updatedAt = memberJson.getString("updated_at"),
            passwordIsSet = memberJson.getBoolean("password_is_set"),
            isPublic = memberJson.getBoolean("is_public"),
            hasCompletedProfile = memberJson.getBoolean("has_completed_profile"),
            licenceNumber = memberJson.getString("licence_number"),
            licenceEndValidity = memberJson.getString("licence_end_validity"),
        )
    }
}