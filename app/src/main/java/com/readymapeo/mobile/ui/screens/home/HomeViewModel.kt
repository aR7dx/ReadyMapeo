package com.readymapeo.mobile.ui.screens.home

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.data.repository.RaidRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _raids = mutableStateOf<List<Raid>>(emptyList())
    val raids: State<List<Raid>> = _raids

    private val database = AppDatabase.getInstance(application)
    private val raidRepository = RaidRepository(database)

    init {
        getLast3Raids()
    }

    private fun getLast3Raids() {
        viewModelScope.launch {
            raidRepository.getLast3Raids().collect { last3Raids ->
                _raids.value = last3Raids
            }
        }
    }
}