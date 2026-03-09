package com.readymapeo.mobile.network

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.readymapeo.mobile.config.ApiConfig
import com.readymapeo.mobile.navigation.redirectRoute
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

                val response = connection.inputStream.bufferedReader().readText()

                Handler(Looper.getMainLooper()).post {
                    callback(response)
                }
            } catch (_: Exception) {
               Handler(Looper.getMainLooper()).post {
                    appContext?.let {
                        Toast.makeText(it, "Impossible de se connecter au serveur", Toast.LENGTH_LONG).show()
                    }
                    redirectRoute("/")
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

    fun put(path: String, body: String, callback: (String) -> Unit) {
        request("PUT", path, body, callback)
    }

    fun patch(path: String, body: String, callback: (String) -> Unit) {
        request("PATCH", path, body, callback)
    }

    fun delete(path: String, body: String, callback: (String) -> Unit) {
        request("DELETE", path, body, callback)
    }
}