package com.readymapeo.mobile.data.repository

import com.readymapeo.mobile.data.api.ClubApiService
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.entity.Club
import com.readymapeo.mobile.data.local.entity.ClubMember
import com.readymapeo.mobile.data.local.entity.User
import com.readymapeo.mobile.network.NetworkConnectivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.withContext

object ClubRepository {

    private lateinit var database: AppDatabase
    private var clubsStateFlow: StateFlow<List<Club>>? = null


    fun setDatabase(appDatabase: AppDatabase) {
        if (!::database.isInitialized) {
            database = appDatabase
        }
    }

    private val clubDao get() = database.clubDao()
    private val userDao get() = database.userDao()
    private val clubMemberDao get() = database.clubMemberDao()

    @OptIn(DelicateCoroutinesApi::class)
    fun getAllClubs(): StateFlow<List<Club>> {
        if (clubsStateFlow == null) {
            clubsStateFlow = flow {
                clubDao.getAll().collect { localClubs ->
                    if (localClubs.isEmpty() && NetworkConnectivity.isOnline.value) {
                        val clubs = ClubApiService.getClubs()

                        clubDao.insertAll(clubs)

                        emit(clubs)
                    } else {
                        emit(localClubs)
                    }
                }
            }.stateIn(GlobalScope, Lazily, emptyList())
        }
        return clubsStateFlow!!
    }

    suspend fun getClubById(clubId: Int): Club? = withContext(Dispatchers.IO) {
        val club = clubDao.getById(clubId)

        if (club == null && NetworkConnectivity.isOnline.value) {
            val clubFromApi = ClubApiService.getClubById(clubId)

            clubDao.insert(clubFromApi)
            return@withContext clubFromApi
        }
        else {
            return@withContext club
        }
    }

    fun getFilteredClubs(search: String): Flow<List<Club>> {
        return clubDao.getFilteredClubs(search)
    }

    suspend fun createClubsMembers(members: List<Pair<User, Int>>) = withContext(Dispatchers.IO) {
        if (members.isEmpty()) {
            return@withContext
        }
        
        members.forEach { memberInfo ->
            userDao.insert(memberInfo.first)

            clubMemberDao.insert(ClubMember(
                memberId = memberInfo.first.id,
                clubId = memberInfo.second,
            ))
        }
    }

    fun getClubsOfAManager(userId: Int): Flow<List<Club>> {
        return clubDao.getClubsOfAManager(userId)
    }
}