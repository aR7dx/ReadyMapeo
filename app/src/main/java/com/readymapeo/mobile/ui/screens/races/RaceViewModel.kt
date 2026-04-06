package com.readymapeo.mobile.ui.screens.races

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.readymapeo.mobile.data.local.entity.Race
import com.readymapeo.mobile.data.repository.RaceRepository
import kotlinx.coroutines.launch

class RaceViewModel(application: Application): AndroidViewModel(application) {

    private val _race = mutableStateOf<Race?>(null)
    val race: State<Race?> = _race

    fun loadRace(raceId: Int) {
        viewModelScope.launch {
            val r = RaceRepository.getRaceById(raceId)

            if (r != null) {
                _race.value = r
            }
        }
    }

}