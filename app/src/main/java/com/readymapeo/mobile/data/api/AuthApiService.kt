package com.readymapeo.mobile.data.api

import com.readymapeo.mobile.network.ApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume

object AuthApiService {

    suspend fun login(email: String, password: String): Result<String> {

        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email and password must not be empty"))
        }

        val body = JSONObject().apply {
            put("email", email)
            put("password", password)
        }.toString()


        return suspendCancellableCoroutine { continuation ->
            ApiClient.post("/login", body) { response ->

                try {
                    val json = JSONObject(response)
                    val status = json.optString("status", "unknown")

                    if (status != "success") {
                        val error = json.optString("message", "Login failed")

                        continuation.resume(Result.failure(Exception(error)))
                        return@post
                    }

                    val data = json.optJSONObject("data")
                    val token = data?.optString("token") ?: ""

                    if (token.isEmpty()) {
                        val error = json.optString("error", "Failed to get token from API response")
                        continuation.resume(Result.failure(Exception(error)))
                    } else {
                        continuation.resume(Result.success(token))
                    }
                } catch (e: Exception) {
                    continuation.resume(Result.failure(e))
                }
            }
        }
    }
}