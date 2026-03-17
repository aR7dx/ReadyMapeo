package com.readymapeo.mobile.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration6To7 : Migration(6, 7) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // In v7, no schema changes detected (same identity hash as v6)
        // This migration is empty but exists for version continuity
    }
}


