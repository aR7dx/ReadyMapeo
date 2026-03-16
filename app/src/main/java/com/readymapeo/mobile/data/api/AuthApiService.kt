package com.readymapeo.mobile.data.api

import com.readymapeo.mobile.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.json.JSONObject
import kotlin.coroutines.resume

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
}
