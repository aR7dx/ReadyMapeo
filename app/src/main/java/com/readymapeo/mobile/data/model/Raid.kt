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
    val racesCount: Int,
    val club: RaidClub,
    val registrationPeriod: RaidRegistrationPeriod
)

data class RaidClub (
    val clubId: Int,
    val clubName: String,
)

data class RaidRegistrationPeriod (
    val insId: Int,
    val insStartDate: String,
    val insEndDate: String,
)