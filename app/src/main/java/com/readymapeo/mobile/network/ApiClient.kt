package com.readymapeo.mobile.network

import android.app.Application
import com.readymapeo.mobile.config.ApiConfig
import com.readymapeo.mobile.manager.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object ApiClient {
    private var appContext: Application? = null

    fun init(context: Application) {
        appContext = context
    }

    private suspend fun request (
        method: String,
        path: String,
        body: String? = null
    ): String = withContext(Dispatchers.IO) {
        try {
            val token = TokenManager.getToken().first()

            val url = URL(ApiConfig.BASE_URL_API + path)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = method
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            token?.let {
                connection.setRequestProperty("Authorization", "Bearer $it")
            }

            if (body != null) {
                connection.doOutput = true
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(body)
                    writer.flush()
                }
            }

            val response = if (connection.responseCode in 200..299) {
                connection.inputStream.bufferedReader().readText()
            } else {
                connection.errorStream?.bufferedReader()?.readText() ?: "{\"error\": \"HTTP ${connection.responseCode}\"}"
            }

            response
        } catch (e: Exception) {
            /* withContext(Dispatchers.Main) {
                appContext?.let {
                    Toast.makeText(it, "Erreur réseau: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } */
            throw e
        }
    }

    suspend fun get(path: String): String {
        return request("GET", path, null)
    }
    suspend fun post(path: String, body: String): String {
        return request("POST", path, body)
    }
    suspend fun put(path: String, body: String): String {
        return request("PUT", path, body)
    }
    suspend fun patch(path: String, body: String): String {
        return request("PATCH", path, body)
    }
    suspend fun delete(path: String, body: String): String {
        return request("DELETE", path, body)
    }
}
