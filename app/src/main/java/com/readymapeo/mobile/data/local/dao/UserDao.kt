package com.readymapeo.mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.readymapeo.mobile.data.local.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query(
        """
        SELECT u.* FROM User u 
        JOIN ClubMember cm on u.id = cm.memberId
        WHERE cm.clubId = :clubId
        """
    )
    fun getMembersByClubId(clubId: Int): Flow<List<User>>

    @Upsert
    fun insert(user: User)

    @Upsert
    fun insertAll(users: List<User>)

}