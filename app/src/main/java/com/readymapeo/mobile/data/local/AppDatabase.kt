package com.readymapeo.mobile.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.readymapeo.mobile.data.local.dao.ClubDao
import com.readymapeo.mobile.data.local.dao.RaidDao
import com.readymapeo.mobile.data.local.entity.Club
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.data.local.migration.Migration3To4

@Database(
    entities = [Club::class, Raid::class],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun clubDao(): ClubDao
    abstract fun raidDao(): RaidDao

    companion object {
        fun getInstance(context: Context): AppDatabase = lazy<AppDatabase> {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, "modules.sqlite"
            )
            //.addMigrations(Migration3To4())
            //.fallbackToDestructiveMigration()
            .build()
        }.value
    }
}