package com.readymapeo.mobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey val id: Int,
    val name: String,
    val lastName: String,
    val firstName: String,
    val description: String,
    val email: String,
    val birthDate: String,
    val address: String,
    val phone: String,
    val active: Int,
    val profilePhotoPath: String,
    val profilePhotoUrl: String,
    val createdAt: String,
    val updatedAt: String,
    val passwordIsSet: Boolean,
    val isPublic: Boolean,
    val hasCompletedProfile: Boolean,
    val licenceNumber: String?,
    val licenceEndValidity: String?,
)