package com.readymapeo.mobile.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Club::class,
            parentColumns = ["clubId"],
            childColumns = ["cluId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Raid(
    @PrimaryKey val raidId: Int,
    val raidName: String,
    val raidDescription: String,
    val adhId: Int,
    val cluId: Int?,
    val insId: Int,
    val raidDateStart: String,
    val raidDateEnd: String,
    val raidContact: String,
    val raidSiteUrl: String? = null,
    val raidImage: String? = null,
    val raidStreet: String,
    val raidCity: String,
    val raidPostalCode: String,
    val raidNumber: Int,
    val createdAt: String,
    val updatedAt: String,
    val racesCount: Int? = null,
    val clubName: String? = null,
    val insStartDate: String,
    val insEndDate: String,
    val isOpen: Boolean? = null,
    val isUpcoming: Boolean? = null,
    val isFinished: Boolean? = null,
    val lastSyncAt: Long = 0,
    val isSynced: Boolean = false,
)