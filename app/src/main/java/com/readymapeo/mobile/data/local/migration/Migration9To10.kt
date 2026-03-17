package com.readymapeo.mobile.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration9To10 : Migration(9, 10) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // In v10, no schema changes detected (same identity hash as v9)
        // This migration is empty but exists for version continuity
    }
}

