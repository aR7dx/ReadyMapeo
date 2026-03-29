package com.readymapeo.mobile.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.readymapeo.mobile.data.local.dao.ClubDao
import com.readymapeo.mobile.data.local.dao.RaidDao
import com.readymapeo.mobile.data.local.dao.AuthenticatedUserDao
import com.readymapeo.mobile.data.local.dao.ClubMemberDao
import com.readymapeo.mobile.data.local.dao.UserDao
import com.readymapeo.mobile.data.local.entity.Club
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.data.local.entity.AuthenticatedUser
import com.readymapeo.mobile.data.local.entity.ClubMember
import com.readymapeo.mobile.data.local.entity.User
import com.readymapeo.mobile.data.local.migration.Migration10To11
import com.readymapeo.mobile.data.local.migration.Migration11To12
import com.readymapeo.mobile.data.local.migration.Migration1To3
import com.readymapeo.mobile.data.local.migration.Migration3To4
import com.readymapeo.mobile.data.local.migration.Migration4To5
import com.readymapeo.mobile.data.local.migration.Migration5To6
import com.readymapeo.mobile.data.local.migration.Migration6To7
import com.readymapeo.mobile.data.local.migration.Migration7To8
import com.readymapeo.mobile.data.local.migration.Migration8To9
import com.readymapeo.mobile.data.local.migration.Migration9To10

@Database(
    entities = [
        AuthenticatedUser::class,
        User::class,
        Club::class,
        ClubMember::class,
        Raid::class,
    ],
    version = 12
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun authenticatedUserDao(): AuthenticatedUserDao
    abstract fun userDao(): UserDao
    abstract fun clubDao(): ClubDao
    abstract fun clubMemberDao(): ClubMemberDao
    abstract fun raidDao(): RaidDao


    companion object {
        fun getInstance(context: Context): AppDatabase = lazy<AppDatabase> {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, "readymapeo.db"
            )
            .addMigrations(
                Migration1To3(),
                Migration3To4(),
                Migration4To5(),
                Migration5To6(),
                Migration6To7(),
                Migration7To8(),
                Migration8To9(),
                Migration9To10(),
                Migration10To11(),
                Migration11To12(),
            )
            .fallbackToDestructiveMigration()
            .build()
        }.value
    }
}


