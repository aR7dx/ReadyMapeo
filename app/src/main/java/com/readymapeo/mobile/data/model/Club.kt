package com.readymapeo.mobile.data.model

data class Club(
    val clubId: Int,
    val clubName: String,
    val clubStreet: String? = null,
    val clubCity: String? = null,
    val clubPostalCode: String? = null,
    val ffsoId: String? = null,
    val description: String? = null,
    val clubImage: String? = null,
    val isApproved: Boolean? = null,
    val approvedBy: Int? = null,
    val approvedAt: String? = null,
    val createdBy: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
)
