package com.readymapeo.mobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Club(
    @PrimaryKey val clubId: Int,
    val clubName: String,
    val clubStreet: String = "",
    val clubCity: String = "",
    val clubPostalCode: String = "",
    val ffsoId: String = "",
    val description: String? = null,
    val clubImage: String? = null,
    val isApproved: Boolean? = null,
    val approvedBy: Int? = null,
    val approvedAt: String? = null,
    val creatorName: String? = null,
    val createdBy: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val isMember: Boolean? = null,
    val isManager: Boolean? = null,
    val membershipStatus: Boolean? = null,
    val lastSyncAt: Long = 0,
    val isSynced: Boolean = false
)