package com.readymapeo.mobile.network

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.readymapeo.mobile.config.ApiConfig
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object ApiClient {
    private var appContext: Application? = null

    fun init(context: Application) {
        appContext = context
    }

    private fun request (method: String, path: String, body: String? = null, callback: (String) -> Unit) {
        Thread {
            try {
                val url = URL(ApiConfig.BASE_URL_API + path)

                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = method
                connection.setRequestProperty("Content-Type", "application/json")

                if (body != null) {
                    connection.doOutput = true
                    val writer = OutputStreamWriter(connection.outputStream)
                    writer.write(body)
                    writer.flush()
                    writer.close()
                }

                val responseCode = connection.responseCode

                val response = if (responseCode in 200..299) {
                    connection.inputStream.bufferedReader().readText()
                } else {
                    connection.errorStream?.bufferedReader()?.readText() ?: "{\"error\": \"HTTP $responseCode\"}"
                }
                
                Handler(Looper.getMainLooper()).post {
                    callback(response)
                }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post {
                    appContext?.let {
                        Toast.makeText(it, "Erreur réseau: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }.start()
    }

    fun get(path: String, callback: (String) -> Unit) {
        request("GET", path, null, callback)
    }

    fun post(path: String, body: String, callback: (String) -> Unit) {
        request("POST", path, body, callback)
    }

    //fun postSync(path: String, body: String): String = requestSync("POST", path, body)

    fun put(path: String, body: String, callback: (String) -> Unit) {
        request("PUT", path, body, callback)
    }

    //fun putSync(path: String, body: String): String = requestSync("PUT", path, body)

    fun patch(path: String, body: String, callback: (String) -> Unit) {
        request("PATCH", path, body, callback)
    }

    //fun patchSync(path: String, body: String): String = requestSync("PATCH", path, body)

    fun delete(path: String, body: String, callback: (String) -> Unit) {
        request("DELETE", path, body, callback)
    }

    //fun deleteSync(path: String, body: String): String = requestSync("DELETE", path, body)
}