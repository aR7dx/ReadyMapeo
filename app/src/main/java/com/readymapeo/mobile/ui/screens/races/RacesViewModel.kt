package com.readymapeo.mobile.ui.screens.races

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.readymapeo.mobile.data.local.entity.Race
import com.readymapeo.mobile.data.repository.RaceRepository
import kotlinx.coroutines.launch

class RacesViewModel(application: Application): AndroidViewModel(application) {

    private val _races = mutableStateOf<List<Race>>(emptyList())
    val races: State<List<Race>> = _races
    val raceSearch = mutableStateOf("")


    init {
        loadRaces()
    }

    private fun loadRaces() {
        viewModelScope.launch {
            RaceRepository.getAllRaces().collect { racesList ->
                _races.value = racesList
            }
        }
    }

    fun filterRaces() {
        viewModelScope.launch() {
            RaceRepository.getFilteredRaces(raceSearch.value)
                .collect { filteredRaces ->
                    _races.value = filteredRaces
                }
        }
    }
}