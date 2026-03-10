package com.readymapeo.mobile.ui.screens.raids

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.data.repository.RaidRepository

class RaidViewModel(application: Application) : AndroidViewModel(application) {
    private val _raid = mutableStateOf<Raid?>(null)
    val raid: State<Raid?> = _raid

    private val database = AppDatabase.getInstance(application)
    private val raidRepository = RaidRepository(database)

    suspend fun loadRaid(raidId: Int) {
        val r = raidRepository.getRaidById(raidId)

        if (r != null) {
            _raid.value = r
        }
    }
}