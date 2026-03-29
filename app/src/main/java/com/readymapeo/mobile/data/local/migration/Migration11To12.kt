package com.readymapeo.mobile.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration11To12 : Migration(11, 12) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Add missing columns to Club table
        db.execSQL("ALTER TABLE `Club` ADD COLUMN `isManager` INTEGER")
        db.execSQL("ALTER TABLE `Club` ADD COLUMN `isMember` INTEGER")
        db.execSQL("ALTER TABLE `Club` ADD COLUMN `membershipStatus` INTEGER")
        
        // Create ClubMember table
        db.execSQL("""
            CREATE TABLE `ClubMember` (
                `clubId` INTEGER NOT NULL,
                `memberId` INTEGER NOT NULL,
                PRIMARY KEY(`clubId`, `memberId`),
                FOREIGN KEY(`clubId`) REFERENCES `Club`(`clubId`) ON UPDATE NO ACTION ON DELETE CASCADE,
                FOREIGN KEY(`memberId`) REFERENCES `User`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
            )
        """)
        
        // Create indices for ClubMember
        db.execSQL("CREATE INDEX `index_ClubMember_clubId` ON `ClubMember` (`clubId`)")
        db.execSQL("CREATE INDEX `index_ClubMember_memberId` ON `ClubMember` (`memberId`)")
        
        // ...existing code...
        // Backup User data before recreating
        db.execSQL("CREATE TABLE `User_backup` AS SELECT * FROM `User`")
        
        // Drop old User table with extra columns
        db.execSQL("DROP TABLE `User`")
        
        // Create new User table matching the current entity structure
        db.execSQL("""
            CREATE TABLE `User` (
                `id` INTEGER NOT NULL,
                `name` TEXT NOT NULL,
                `lastName` TEXT NOT NULL,
                `firstName` TEXT NOT NULL,
                `description` TEXT NOT NULL,
                `email` TEXT NOT NULL,
                `birthDate` TEXT NOT NULL,
                `address` TEXT NOT NULL,
                `phone` TEXT NOT NULL,
                `active` INTEGER NOT NULL,
                `profilePhotoPath` TEXT NOT NULL,
                `profilePhotoUrl` TEXT NOT NULL,
                `createdAt` TEXT NOT NULL,
                `updatedAt` TEXT NOT NULL,
                `passwordIsSet` INTEGER NOT NULL,
                `isPublic` INTEGER NOT NULL,
                `hasCompletedProfile` INTEGER NOT NULL,
                `licenceNumber` TEXT NOT NULL,
                `licenceEndValidity` TEXT NOT NULL,
                PRIMARY KEY(`id`)
            )
        """)
        
        // Copy data from backup to new table (only matching columns)
        db.execSQL("""
            INSERT INTO `User` 
            (id, name, lastName, firstName, description, email, birthDate, address, phone, active, 
             profilePhotoPath, profilePhotoUrl, createdAt, updatedAt, passwordIsSet, isPublic, hasCompletedProfile, 
             licenceNumber, licenceEndValidity)
            SELECT 
            id, name, lastName, firstName, COALESCE(description, ''), email, birthDate, address, phone, active, 
            COALESCE(profilePhotoPath, ''), COALESCE(profilePhotoUrl, ''), createdAt, COALESCE(updatedAt, ''), passwordIsSet, 
            isPublic, hasCompletedProfile, COALESCE(licenseNumber, ''), COALESCE(licenseEndValidity, '')
            FROM `User_backup`
        """)
        
        // Drop backup
        db.execSQL("DROP TABLE `User_backup`")
        
        // Create AuthenticatedUser table (separate from User)
        db.execSQL("""
            CREATE TABLE `AuthenticatedUser` (
                `id` INTEGER NOT NULL,
                `docId` INTEGER,
                `adhId` INTEGER,
                `name` TEXT NOT NULL,
                `lastName` TEXT NOT NULL,
                `firstName` TEXT NOT NULL,
                `email` TEXT NOT NULL,
                `birthDate` TEXT NOT NULL,
                `address` TEXT NOT NULL,
                `phone` TEXT NOT NULL,
                `description` TEXT,
                `active` INTEGER NOT NULL,
                `emailVerifiedAt` TEXT,
                `twoFactorConfirmedAt` TEXT,
                `currentTeamId` INTEGER,
                `profilePhotoPath` TEXT,
                `profilePhotoUrl` TEXT,
                `createdAt` TEXT NOT NULL,
                `updatedAt` TEXT,
                `passwordIsSet` INTEGER NOT NULL,
                `isPublic` INTEGER NOT NULL,
                `hasCompletedProfile` INTEGER NOT NULL,
                `licenseNumber` INTEGER,
                `licenseEndValidity` TEXT,
                PRIMARY KEY(`id`)
            )
        """)
    }
}







