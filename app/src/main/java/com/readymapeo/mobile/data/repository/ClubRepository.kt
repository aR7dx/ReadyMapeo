package com.readymapeo.mobile.data.repository

import com.readymapeo.mobile.data.api.ClubApiService
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.entity.Club
import com.readymapeo.mobile.network.NetworkConnectivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ClubRepository(private val database: AppDatabase) {

    private val clubApiService = ClubApiService()
    private val clubDao = database.clubDao()

    fun getAllClubs(): Flow<List<Club>> = flow {

        clubDao.getAll().collect { localClubs ->
            //println("${localClubs.size} clubs trouvés en bdd")

            if (localClubs.isNotEmpty()) {
                emit(localClubs)
            }
            else if (NetworkConnectivity.isOnline.value) {
                val clubsFromApi = clubApiService.getClubs()

                clubDao.insertAll(clubsFromApi)
                emit(clubsFromApi)
            }
            else {
                emit(localClubs)
            }
        }
    }

    suspend fun getClubById(clubId: Int): Club? {
        val club = clubDao.getById(clubId)

        if (club != null) {
            return club
        }
        else if (NetworkConnectivity.isOnline.value) {
            val clubFromApi = clubApiService.getClubById(clubId)

            clubDao.insert(clubFromApi!!)
            return clubFromApi
        }
        else {
            return club
        }
    }
}