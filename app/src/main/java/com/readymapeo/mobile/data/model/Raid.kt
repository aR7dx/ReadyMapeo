package com.readymapeo.mobile.data.model

data class Raid (
    val raidId: Int,
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
    val club: Club,
    val registrationPeriod: RaidRegistrationPeriod
)

data class RaidRegistrationPeriod (
    val insId: Int,
    val insStartDate: String,
    val insEndDate: String,
)