package com.readymapeo.mobile.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration2To3 : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Create Club table
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS `Club` (
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

