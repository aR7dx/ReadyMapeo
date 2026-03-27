package com.readymapeo.mobile.ui.screens.raids

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.readymapeo.mobile.manager.RolesManager
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.data.repository.RaidRepository
import com.readymapeo.mobile.utils.UserRole
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.distinctUntilChanged

class RaidsViewModel(application: Application) : AndroidViewModel(application) {

    val roles = mutableStateOf(emptyList<String>())
    private val _raids = mutableStateOf<List<Raid>>(emptyList())
    val raids: State<List<Raid>> = _raids

    val locationScopes = listOf("Ville", "Département", "Région")
    val raidType = listOf("Tous", "Loisir", "Compétition")
    val raidCategory = listOf("Tous", "Benjamins", "Minimes", "Cadets", "Juniors", "Espoirs", "Séniors", "Vétérans")

    // mutable
    val selectedLocationScope = mutableStateOf(locationScopes[0])
    val locationInputValue = mutableStateOf("")
    val selectedDate = mutableStateOf<Long?>(null)
    val selectedType = mutableStateOf(raidType[0])
    val selectedCategory = mutableStateOf(raidCategory[0])

    init {
        viewModelScope.launch {
            RolesManager.getRolesFlow()
                .distinctUntilChanged()
                .collect { rolesList ->
                    roles.value = rolesList
                }
        }

        loadRaids()
    }

    private fun loadRaids() {
        viewModelScope.launch {
            RaidRepository.getAllRaids().collect { raids ->
                _raids.value = raids
            }
        }
    }

    fun filterRaids(locationScope: String, inputValue: String?, date: Long?) {
        viewModelScope.launch {
            RaidRepository.getFilteredRaids(locationScope, inputValue, date)
                .distinctUntilChanged()
                .collect { filteredRaids ->
                    selectedLocationScope.value = locationScope
                    locationInputValue.value = inputValue ?: ""
                    selectedDate.value = date

                    _raids.value = filteredRaids
                }
        }
    }

    fun hasRole(role: UserRole): Boolean {
        return roles.value.contains(role.roleName)
    }
}