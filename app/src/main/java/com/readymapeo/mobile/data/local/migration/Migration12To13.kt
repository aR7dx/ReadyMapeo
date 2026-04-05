package com.readymapeo.mobile.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration12To13 : Migration(12, 13) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Create Race table
        db.execSQL("""
            CREATE TABLE `Race` (
                `raceId` INTEGER NOT NULL,
                `raceName` TEXT NOT NULL,
                `raceDescription` TEXT NOT NULL,
                `raceDateStart` TEXT NOT NULL,
                `raceDateEnd` TEXT NOT NULL,
                `raceDifficulty` TEXT NOT NULL,
                `raceDurationMinutes` INTEGER NOT NULL,
                `raceType` TEXT NOT NULL,
                `raceImageUrl` TEXT NOT NULL,
                `racePriceMajor` INTEGER NOT NULL,
                `racePriceMinor` INTEGER NOT NULL,
                `raceIsOpen` INTEGER NOT NULL,
                `raceRaidId` INTEGER NOT NULL,
                `raceRaidName` TEXT NOT NULL,
                `raceRaidCity` TEXT NOT NULL,
                `raceClubId` INTEGER NOT NULL,
                `raceClubName` TEXT NOT NULL,
                `raceOrganizerName` TEXT NOT NULL,
                `raceAgeCategories` TEXT,
                PRIMARY KEY(`raceId`),
                FOREIGN KEY(`raceRaidId`) REFERENCES `Raid`(`raidId`) ON UPDATE NO ACTION ON DELETE CASCADE
            )
        """)
    }
}

