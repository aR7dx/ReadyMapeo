package com.readymapeo.mobile.ui.screens.raids

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.entity.Race
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.data.repository.RaceRepository
import com.readymapeo.mobile.data.repository.RaidRepository
import kotlinx.coroutines.launch

class RaidViewModel(application: Application) : AndroidViewModel(application) {
    private val _raid = mutableStateOf<Raid?>(null)
    val raid: State<Raid?> = _raid

    private val _raidRaces = mutableStateOf<List<Race>>(emptyList())
    val raidRaces: State<List<Race>> = _raidRaces

    fun loadRaid(raidId: Int) {
        viewModelScope.launch {
            val r = RaidRepository.getRaidById(raidId)

            if (r != null) {
                _raid.value = r
            }
        }
    }

    fun loadRaidRaces(raidId: Int) {
        viewModelScope.launch {
            RaceRepository.getRacesByRaidId(raidId).collect { races ->
                _raidRaces.value = races
            }
        }
    }
}