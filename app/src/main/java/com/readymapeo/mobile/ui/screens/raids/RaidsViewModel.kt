package com.readymapeo.mobile.ui.screens.raids

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.data.repository.RaidRepository
import kotlinx.coroutines.launch

class RaidsViewModel(application: Application) : AndroidViewModel(application) {
    private val _raids = mutableStateOf<List<Raid>>(emptyList())
    val raids: State<List<Raid>> = _raids

    private val database = AppDatabase.getInstance(application)
    private val raidRepository = RaidRepository(database)

    init {
        loadRaids()
    }

    private fun loadRaids() {
        viewModelScope.launch {
            raidRepository.getAllRaids().collect { raids ->
                _raids.value = raids
            }
        }
    }

    fun filterRaids(locationScope: String?, locationInputValue: String?, date: Long?) {
        viewModelScope.launch {
            raidRepository.getFilteredRaids(locationScope, locationInputValue, date).collect { filteredRaids ->
                _raids.value = filteredRaids
            }
        }
    }
}