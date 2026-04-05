package com.readymapeo.mobile.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

object NetworkConnectivity {
    private val _isOnline = MutableStateFlow(false)
    val isOnline: StateFlow<Boolean> = _isOnline

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun init(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        scope.launch {
            updateOnlineStatus(connectivityManager)
        }

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                scope.launch { updateOnlineStatus(connectivityManager) }
            }

            override fun onLost(network: Network) {
                scope.launch { updateOnlineStatus(connectivityManager) }
            }
        }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private suspend fun updateOnlineStatus(connectivityManager: ConnectivityManager) {
        val isNetworkOk = isNetworkAvailable(connectivityManager)
        val isServerOk = if (isNetworkOk) isServerAvailable() else false
        val newStatus = isNetworkOk && isServerOk

        _isOnline.value = newStatus

        NetworkStatusManager.notifyObservers(newStatus)
    }

    private fun isNetworkAvailable(connectivityManager: ConnectivityManager): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private suspend fun isServerAvailable(): Boolean {
        return try {
            ApiClient.get("/up")
            true
        } catch (_: Exception) {
            false
        }
    }
}