package com.readymapeo.mobile.ui.screens.raids

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.readymapeo.mobile.data.model.Raid
import com.readymapeo.mobile.data.repository.RaidRepository

class RaidsViewModel : ViewModel() {
    var raids = mutableStateOf<List<Raid>>(emptyList())
    private set

    private val repository = RaidRepository()

    fun loadRaids() {
        repository.getRaids {
            raids.value = it
        }
    }
}