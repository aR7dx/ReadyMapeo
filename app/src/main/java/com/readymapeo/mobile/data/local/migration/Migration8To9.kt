package com.readymapeo.mobile.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration8To9 : Migration(8, 9) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Backup current data
        database.execSQL("CREATE TABLE `Club_backup` AS SELECT * FROM `Club`")
        database.execSQL("CREATE TABLE `Raid_backup` AS SELECT * FROM `Raid`")

        // Drop the old table
        database.execSQL("DROP TABLE `Club`")
        database.execSQL("DROP TABLE `Raid`")

        // Recreate Club and Raid table with ON DELETE CASCADE instead of ON DELETE SET NULL
        database.execSQL("""
            CREATE TABLE `Club` (
                `clubId` INTEGER NOT NULL,
                `clubName` TEXT NOT NULL,
                `clubStreet` TEXT NOT NULL,
                `clubCity` TEXT NOT NULL,
                `clubPostalCode` TEXT NOT NULL,
                `ffsoId` TEXT NOT NULL,
                `description` TEXT,
                `clubImage` TEXT,
                `isApproved` INTEGER,
                `approvedBy` INTEGER,
                `approvedAt` TEXT,
                `createdBy` INTEGER,
                `createdAt` TEXT,
                `updatedAt` TEXT,
                `lastSyncAt` INTEGER NOT NULL,
                `isSynced` INTEGER NOT NULL,
                PRIMARY KEY(`clubId`)
            )
        """)

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
                `raidSiteUrl` TEXT,
                `raidImage` TEXT,
                `raidStreet` TEXT NOT NULL,
                `raidCity` TEXT NOT NULL,
                `raidPostalCode` TEXT NOT NULL,
                `raidNumber` INTEGER NOT NULL,
                `createdAt` TEXT NOT NULL,
                `updatedAt` TEXT NOT NULL,
                `racesCount` INTEGER,
                `clubName` TEXT,
                `insStartDate` TEXT NOT NULL,
                `insEndDate` TEXT NOT NULL,
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
        //database.execSQL("INSERT INTO `Club` SELECT * FROM `Club_backup`")
        //database.execSQL("INSERT INTO `Raid` SELECT * FROM `Raid_backup`")

        // Drop backup table
        database.execSQL("DROP TABLE `Club_backup`")
        database.execSQL("DROP TABLE `Raid_backup`")
    }
}

