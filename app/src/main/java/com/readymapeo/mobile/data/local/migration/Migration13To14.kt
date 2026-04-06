package com.readymapeo.mobile.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration13To14 : Migration(13, 14) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE User_new (
                id INTEGER NOT NULL PRIMARY KEY,
                name TEXT NOT NULL,
                lastName TEXT NOT NULL,
                firstName TEXT NOT NULL,
                description TEXT NOT NULL,
                email TEXT NOT NULL,
                birthDate TEXT NOT NULL,
                address TEXT NOT NULL,
                phone TEXT NOT NULL,
                active INTEGER NOT NULL,
                profilePhotoPath TEXT NOT NULL,
                profilePhotoUrl TEXT NOT NULL,
                createdAt TEXT NOT NULL,
                updatedAt TEXT NOT NULL,
                passwordIsSet INTEGER NOT NULL,
                isPublic INTEGER NOT NULL,
                hasCompletedProfile INTEGER NOT NULL,
                licenceNumber TEXT,
                licenceEndValidity TEXT
            )
        """)

        // Copy data from old table to new table
        db.execSQL("""
            INSERT INTO User_new (
                id, name, lastName, firstName, description, email, birthDate, address, phone,
                active, profilePhotoPath, profilePhotoUrl, createdAt, updatedAt, passwordIsSet,
                isPublic, hasCompletedProfile, licenceNumber, licenceEndValidity
            ) SELECT
                id, name, lastName, firstName, description, email, birthDate, address, phone,
                active, profilePhotoPath, profilePhotoUrl, createdAt, updatedAt, passwordIsSet,
                isPublic, hasCompletedProfile, licenceNumber, licenceEndValidity
            FROM User
        """)

        // Drop old table and rename new one
        db.execSQL("DROP TABLE User")
        db.execSQL("ALTER TABLE User_new RENAME TO User")
    }
}

