package com.readymapeo.mobile.network

/**
 * Interface pour le pattern Observer
 * Permet aux composants de s'observer mutuellement sur les changements d'état réseau
 */
interface NetworkObserver {

    fun onNetworkStatusChanged(isOnline: Boolean)
}

