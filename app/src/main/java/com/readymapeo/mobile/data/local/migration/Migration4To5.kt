package com.readymapeo.mobile.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration4To5 : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Backup Raid data
        db.execSQL("CREATE TABLE `Raid_backup` AS SELECT * FROM `Raid`")

        // Drop old Raid table
        db.execSQL("DROP TABLE `Raid`")

        // Recreate Raid table with updated foreign key (ON DELETE CASCADE)
        db.execSQL("""
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
                `clubName` TEXT,
                `isOpen` INTEGER,
                `isUpcoming` INTEGER,
                `isFinished` INTEGER,
                `lastSyncAt` INTEGER NOT NULL,
                `isSynced` INTEGER NOT NULL,
                PRIMARY KEY(`raidId`),
                FOREIGN KEY(`cluId`) REFERENCES `Club`(`clubId`) ON UPDATE NO ACTION ON DELETE CASCADE
            )
        """)

        // Copy data from backup
        db.execSQL("""
            INSERT INTO `Raid` 
            (raidId, raidName, raidDescription, adhId, cluId, insId, raidDateStart, raidDateEnd, 
             raidContact, raidSiteUrl, raidImage, raidStreet, raidCity, raidPostalCode, raidNumber, 
             createdAt, updatedAt, racesCount, isOpen, isUpcoming, isFinished, lastSyncAt, isSynced)
            SELECT 
            raidId, raidName, raidDescription, adhId, cluId, insId, raidDateStart, raidDateEnd, 
            raidContact, raidSiteUrl, raidImage, raidStreet, raidCity, raidPostalCode, raidNumber, 
            createdAt, updatedAt, racesCount, isOpen, isUpcoming, isFinished, lastSyncAt, isSynced
            FROM `Raid_backup`
        """)

        // Drop backup table
        db.execSQL("DROP TABLE `Raid_backup`")
    }
}



