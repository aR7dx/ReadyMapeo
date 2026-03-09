package com.readymapeo.mobile.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.readymapeo.mobile.data.local.dao.RaidDao
import com.readymapeo.mobile.data.local.entity.Raid

@Database(
    entities = [Raid::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun raidDao(): RaidDao

    companion object {
        fun getInstance(context: Context): AppDatabase = lazy {
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, "modules.sqlite"
            ).build()
        }.value
    }
}