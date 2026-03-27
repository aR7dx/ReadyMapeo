package com.readymapeo.mobile.data.api

import com.readymapeo.mobile.manager.RolesManager
import com.readymapeo.mobile.data.local.entity.User
import com.readymapeo.mobile.network.ApiClient
import com.readymapeo.mobile.utils.optIntOrNull
import com.readymapeo.mobile.utils.optStringOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

object AuthApiService {

    suspend fun login(email: String, password: String): Result<String> = withContext(Dispatchers.IO) {

        if (email.isBlank() || password.isBlank()) {
            return@withContext Result.failure(IllegalArgumentException("Email and password must not be empty"))
        }

        val body = JSONObject().apply {
            put("email", email)
            put("password", password)
        }.toString()


        try {
            val response = ApiClient.post("/login", body)

            val json = JSONObject(response)
            val status = json.optString("status", "unknown")

            if (status != "success") {
                val error = json.optString("message", "Login failed")

                return@withContext Result.failure(Exception(error))
            }

            val data = json.optJSONObject("data")
            val token = data?.optString("token") ?: ""

            if (token.isEmpty()) {
                val error = json.optString("error", "Failed to get token from API response")
                return@withContext Result.failure(Exception(error))
            }

            Result.success(token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserInfo(): User = withContext(Dispatchers.IO) {
        try {
            val response = ApiClient.get("/user")

            val json = JSONObject(response)
            val data = json.getJSONObject("data")
            
            val rolesArray = data.optJSONArray("roles")
            val roles = parseUserRolesJson(rolesArray)
            
            RolesManager.saveRoles(roles)

            return@withContext parseUserJson(data)
        }
        catch (e: Exception) {
            throw e
        }
    }

    fun parseUserJson(userJson: JSONObject): User {
        return User(
            id = userJson.getInt("id"),
            docId = userJson.optIntOrNull("doc_id"),
            adhId = userJson.optIntOrNull("adh_id"),
            name = userJson.getString("name"),
            lastName = userJson.getString("last_name"),
            firstName = userJson.getString("first_name"),
            email = userJson.getString("email"),
            birthDate = userJson.getString("birth_date"),
            address = userJson.getString("address"),
            phone = userJson.getString("phone"),
            description = userJson.optStringOrNull("description"),
            active = userJson.getInt("active"),
            emailVerifiedAt = userJson.optStringOrNull("email_verified_at"),
            twoFactorConfirmedAt = userJson.optStringOrNull("two_factor_confirmed_at"),
            currentTeamId = userJson.optIntOrNull("current_team_id"),
            profilePhotoPath = userJson.optStringOrNull("profile_photo_path"),
            profilePhotoUrl = userJson.optStringOrNull("profile_photo_url"),
            createdAt = userJson.getString("created_at"),
            updatedAt = userJson.optStringOrNull("updated_at"),
            passwordIsSet = userJson.getBoolean("password_is_set"),
            isPublic = userJson.getBoolean("is_public"),
            hasCompletedProfile = userJson.getBoolean("has_completed_profile"),
            licenseNumber = userJson.getLong("license_number"),
            licenseEndValidity = userJson.optStringOrNull("licence_end_validity")
        )
    }

    fun parseUserRolesJson(rolesArray: Any?): List<String> {
        return if (rolesArray is org.json.JSONArray) {
            val roles = mutableListOf<String>()
            for (i in 0 until rolesArray.length()) {
                roles.add(rolesArray.getString(i))
            }
            roles
        } else {
            emptyList()
        }
    }
}