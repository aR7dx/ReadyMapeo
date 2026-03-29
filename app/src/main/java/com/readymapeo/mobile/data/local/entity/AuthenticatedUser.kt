package com.readymapeo.mobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AuthenticatedUser (
    @PrimaryKey val id: Int,
    val docId: Int? = null,
    val adhId: Int? = null,
    val name: String,
    val lastName: String,
    val firstName: String,
    val email: String,
    val birthDate: String,
    val address: String,
    val phone: String,
    val description: String? = null,
    val active: Int,
    val emailVerifiedAt: String? = null,
    val twoFactorConfirmedAt: String? = null,
    val currentTeamId: Int? = null,
    val profilePhotoPath: String? = null,
    val profilePhotoUrl: String? = null,
    val createdAt: String,
    val updatedAt: String? = null,
    val passwordIsSet: Boolean,
    val isPublic: Boolean,
    val hasCompletedProfile: Boolean,
    val licenseNumber: Long? = null,
    val licenseEndValidity: String? = null,
)