package com.readymapeo.mobile.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration7To8 : Migration(7, 8) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // In v8, no schema changes detected (same identity hash as v7)
        // This migration is empty but exists for version continuity
    }
}


