package com.readymapeo.mobile.network

/**
 * Gestionnaire centralisé pour les observateurs du statut réseau
 * Implémente le pattern Observer
 */
object NetworkStatusManager {
    private val observers = mutableSetOf<NetworkObserver>()

    fun addObserver(observer: NetworkObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: NetworkObserver) {
        observers.remove(observer)
    }

    fun notifyObservers(isOnline: Boolean) {
        for (observer in observers) {
            observer.onNetworkStatusChanged(isOnline)
        }
    }

    fun clearObservers() {
        observers.clear()
    }
}

