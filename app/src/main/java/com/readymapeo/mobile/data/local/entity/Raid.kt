package com.readymapeo.mobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Raid(
    @PrimaryKey val raidId: Int,
    val raidName: String,
    val raidDescription: String,
    val adhId: Int,
    val cluId: Int,
    val insId: Int,
    val raidDateStart: String,
    val raidDateEnd: String,
    val raidContact: String,
    val raidSiteUrl: String,
    val raidImage: String,
    val raidStreet: String,
    val raidCity: String,
    val raidPostalCode: String,
    val raidNumber: Int,
    val createdAt: String,
    val updatedAt: String,
    val isOpen: Boolean? = null,
    val isUpcoming: Boolean? = null,
    val isFinished: Boolean? = null,
    val racesCount: Int? = null,
)