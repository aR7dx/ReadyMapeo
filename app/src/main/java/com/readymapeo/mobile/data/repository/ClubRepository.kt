package com.readymapeo.mobile.data.repository

import com.readymapeo.mobile.data.api.ClubApiService
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.entity.Club
import com.readymapeo.mobile.network.NetworkConnectivity

class ClubRepository(private val database: AppDatabase) {

    private val clubApiService = ClubApiService()
    private val clubDao = database.clubDao()

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