package com.readymapeo.mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.readymapeo.mobile.data.local.entity.ClubMember

@Dao
interface ClubMemberDao {

    @Upsert
    fun insert(clubMember: ClubMember)

}