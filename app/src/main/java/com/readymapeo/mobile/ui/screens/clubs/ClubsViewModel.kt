package com.readymapeo.mobile.ui.screens.clubs

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.readymapeo.mobile.data.local.entity.Club
import com.readymapeo.mobile.data.repository.ClubRepository

class ClubsViewModel(application: Application) : AndroidViewModel(application) {
    private val _clubs = mutableStateOf<List<Club>>(emptyList())
    val clubs: State<List<Club>> = _clubs

    suspend fun loadClubs() {
        ClubRepository.getAllClubs().collect {
            _clubs.value = it
        }
    }
}