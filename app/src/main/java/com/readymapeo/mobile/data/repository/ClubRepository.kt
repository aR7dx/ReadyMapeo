package com.readymapeo.mobile.data.repository

import com.readymapeo.mobile.data.api.ClubApiService
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.entity.Club
import com.readymapeo.mobile.network.NetworkConnectivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily

object ClubRepository {

    private lateinit var database: AppDatabase
    private var clubsStateFlow: StateFlow<List<Club>>? = null

    fun setDatabase(appDatabase: AppDatabase) {
        if (!::database.isInitialized) {
            database = appDatabase
        }
    }

    private val clubDao get() = database.clubDao()

    @OptIn(DelicateCoroutinesApi::class)
    fun getAllClubs(): StateFlow<List<Club>> {
        if (clubsStateFlow == null) {
            clubsStateFlow = flow {
                clubDao.getAll().collect { localClubs ->
                    if (localClubs.isEmpty() && NetworkConnectivity.isOnline.value) {
                        val clubsFromApi = ClubApiService.getClubs()
                        clubDao.insertAll(clubsFromApi)
                        emit(clubsFromApi)
                    } else {
                        emit(localClubs)
                    }
                }
            }.stateIn(GlobalScope, Lazily, emptyList())
        }
        return clubsStateFlow!!
    }

    suspend fun getClubById(clubId: Int): Club? {
        val club = clubDao.getById(clubId)

        if (club == null && NetworkConnectivity.isOnline.value) {
            val clubFromApi = ClubApiService.getClubById(clubId)

            clubDao.insert(clubFromApi!!)
            return clubFromApi
        }
        else {
            return club
        }
    }
}