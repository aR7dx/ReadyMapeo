package com.readymapeo.mobile.ui.screens.raids

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.data.repository.RaidRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class RaidsViewModel(application: Application) : AndroidViewModel(application) {
    private val _raids = mutableStateOf<List<Raid>>(emptyList())
    val raids: State<List<Raid>> = _raids

    private val database = AppDatabase.getInstance(application)
    private val raidRepository = RaidRepository(database)

    suspend fun loadRaids() {
        raidRepository.getAllRaids().collect {
            _raids.value = it
        }
    }
}