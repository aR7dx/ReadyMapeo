package com.readymapeo.mobile.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration4To5 : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Backup current data
        database.execSQL("""
            CREATE TABLE `Raid_backup` AS SELECT * FROM `Raid`
        """)

        // Drop the old table
        database.execSQL("DROP TABLE `Raid`")

        // Recreate Raid table with raidImage as nullable and cluId nullable with CASCADE delete
        database.execSQL("""
            CREATE TABLE `Raid` (
                `raidId` INTEGER NOT NULL,
                `raidName` TEXT NOT NULL,
                `raidDescription` TEXT NOT NULL,
                `adhId` INTEGER NOT NULL,
                `cluId` INTEGER,
                `insId` INTEGER NOT NULL,
                `raidDateStart` TEXT NOT NULL,
                `raidDateEnd` TEXT NOT NULL,
                `raidContact` TEXT NOT NULL,
                `raidSiteUrl` TEXT NOT NULL,
                `raidImage` TEXT,
                `raidStreet` TEXT NOT NULL,
                `raidCity` TEXT NOT NULL,
                `raidPostalCode` TEXT NOT NULL,
                `raidNumber` INTEGER NOT NULL,
                `createdAt` TEXT NOT NULL,
                `updatedAt` TEXT NOT NULL,
                `racesCount` INTEGER,
                `isOpen` INTEGER,
                `isUpcoming` INTEGER,
                `isFinished` INTEGER,
                `lastSyncAt` INTEGER NOT NULL,
                `isSynced` INTEGER NOT NULL,
                PRIMARY KEY(`raidId`),
                FOREIGN KEY(`cluId`) REFERENCES `Club`(`clubId`) ON UPDATE NO ACTION ON DELETE CASCADE
            )
        """)

        // Restore data
        database.execSQL("""
            INSERT INTO `Raid` SELECT * FROM `Raid_backup`
        """)

        // Drop backup table
        database.execSQL("DROP TABLE `Raid_backup`")
    }
}

