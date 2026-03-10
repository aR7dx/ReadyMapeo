package com.readymapeo.mobile.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration3To4 : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // First, handle Raid table - drop the old one and create new one with foreign key
        database.execSQL("DROP TABLE IF EXISTS `Raid`")

        database.execSQL("""
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
                `isOpen` INTEGER,
                `isUpcoming` INTEGER,
                `isFinished` INTEGER,
                `lastSyncAt` INTEGER NOT NULL,
                `isSynced` INTEGER NOT NULL,
                PRIMARY KEY(`raidId`),
                FOREIGN KEY(`cluId`) REFERENCES `Club`(`clubId`) ON UPDATE NO ACTION ON DELETE SET NULL
            )
        """)

        // Recreate Club table with correct schema (drop and recreate)
        database.execSQL("DROP TABLE IF EXISTS `Club`")

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
    }
}

