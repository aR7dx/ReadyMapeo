package com.readymapeo.mobile.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration1To3 : Migration(1, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Create Club table
        db.execSQL("""
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

        // Backup Raid data
        db.execSQL("CREATE TABLE `Raid_backup` AS SELECT * FROM `Raid`")

        // Drop old Raid table
        db.execSQL("DROP TABLE `Raid`")

        // Recreate Raid table with 'club' column
        db.execSQL("""
            CREATE TABLE `Raid` (
                `raidId` INTEGER NOT NULL,
                `raidName` TEXT NOT NULL,
                `raidDescription` TEXT NOT NULL,
                `adhId` INTEGER NOT NULL,
                `cluId` INTEGER NOT NULL,
                `insId` INTEGER NOT NULL,
                `raidDateStart` TEXT NOT NULL,
                `raidDateEnd` TEXT NOT NULL,
                `raidContact` TEXT NOT NULL,
                `raidSiteUrl` TEXT NOT NULL,
                `raidImage` TEXT NOT NULL,
                `raidStreet` TEXT NOT NULL,
                `raidCity` TEXT NOT NULL,
                `raidPostalCode` TEXT NOT NULL,
                `raidNumber` INTEGER NOT NULL,
                `createdAt` TEXT NOT NULL,
                `updatedAt` TEXT NOT NULL,
                `racesCount` INTEGER,
                `club` TEXT NOT NULL,
                `isOpen` INTEGER,
                `isUpcoming` INTEGER,
                `isFinished` INTEGER,
                `lastSyncAt` INTEGER NOT NULL,
                `isSynced` INTEGER NOT NULL,
                PRIMARY KEY(`raidId`)
            )
        """)

        // Copy data from backup, using clubName as club value
        db.execSQL("""
            INSERT INTO `Raid` 
            (raidId, raidName, raidDescription, adhId, cluId, insId, raidDateStart, raidDateEnd, 
             raidContact, raidSiteUrl, raidImage, raidStreet, raidCity, raidPostalCode, raidNumber, 
             createdAt, updatedAt, racesCount, club, isOpen, isUpcoming, isFinished, lastSyncAt, isSynced)
            SELECT 
            raidId, raidName, raidDescription, adhId, cluId, insId, raidDateStart, raidDateEnd, 
            raidContact, raidSiteUrl, raidImage, raidStreet, raidCity, raidPostalCode, raidNumber, 
            createdAt, updatedAt, racesCount, clubName, isOpen, isUpcoming, isFinished, lastSyncAt, isSynced
            FROM `Raid_backup`
        """)

        // Drop backup table
        db.execSQL("DROP TABLE `Raid_backup`")
    }
}





