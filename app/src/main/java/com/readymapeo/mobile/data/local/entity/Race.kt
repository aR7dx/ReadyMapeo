package com.readymapeo.mobile.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Raid::class,
            parentColumns = ["raidId"],
            childColumns = ["raceRaidId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Race (
    @PrimaryKey val raceId: Int,
    val raceName: String,
    val raceDescription: String,
    val raceDateStart: String,
    val raceDateEnd: String,
    val raceDifficulty: String,
    val raceDurationMinutes: Int,
    val raceType: String,
    val raceImageUrl: String,
    val racePriceMajor: Int,
    val racePriceMinor: Int,
    val raceIsOpen: Boolean,
    val raceRaidId: Int,
    val raceRaidName: String,
    val raceRaidCity: String,
    val raceClubId: Int,
    val raceClubName: String,
    val raceOrganizerName: String,
    val raceAgeCategories: String?,
)