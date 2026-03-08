package com.readymapeo.mobile.data.repository

import com.readymapeo.mobile.data.api.ReadyMapeoApi
import com.readymapeo.mobile.data.model.Raid

class RaidRepository {
    private val api = ReadyMapeoApi()

    fun getRaids(callback: (List<Raid>) -> Unit) {
        api.getRaids(callback)
    }
}