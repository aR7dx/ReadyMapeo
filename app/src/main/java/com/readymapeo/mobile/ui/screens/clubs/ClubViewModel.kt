package com.readymapeo.mobile.ui.screens.clubs

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.readymapeo.mobile.data.local.entity.Club
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.data.local.entity.User
import com.readymapeo.mobile.data.repository.AuthRepository
import com.readymapeo.mobile.data.repository.ClubRepository
import com.readymapeo.mobile.data.repository.RaidRepository
import com.readymapeo.mobile.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ClubViewModel(application: Application): AndroidViewModel(application) {

    private val _club = mutableStateOf<Club?>(null)
    val club: State<Club?> = _club

    private val _clubRaids = mutableStateOf<List<Raid>>(emptyList())
    val clubRaids: State<List<Raid>> = _clubRaids

    private val _clubMembers = mutableStateOf<List<User>>(emptyList())
    val clubMembers: State<List<User>> = _clubMembers

    private val _isMember = MutableStateFlow(false)
    val isMember: StateFlow<Boolean> = _isMember

    fun loadClub(clubId: Int) {
        viewModelScope.launch {
            val c = ClubRepository.getClubById(clubId)

            if (c != null) {
                _club.value = c
            }
        }
    }

    fun loadClubRaids(clubId: Int) {
        viewModelScope.launch {
            RaidRepository.getRaidsByClubId(clubId).collect { raids ->
                _clubRaids.value = raids
            }
        }
    }

    fun loadClubMembers(clubId: Int) {
        viewModelScope.launch {
            UserRepository.getMembersByClubId(clubId).collect { members ->
                _clubMembers.value = members
                checkIfUserIsMember()
            }
        }
    }

    private fun checkIfUserIsMember() {
        viewModelScope.launch {
            try {
                val authUser = AuthRepository.getAuthenticatedUserInfo().first()
                val isMemberResult = clubMembers.value.any { member ->
                    member.id == authUser.id && member.email == authUser.email
                }

                _isMember.value = isMemberResult
            }
            catch (e: Exception) {
                _isMember.value = false
            }
        }
    }

}