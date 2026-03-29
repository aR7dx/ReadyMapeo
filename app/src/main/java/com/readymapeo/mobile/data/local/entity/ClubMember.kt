package com.readymapeo.mobile.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    primaryKeys = ["clubId", "memberId"],
    foreignKeys = [
        ForeignKey(
            entity = Club::class,
            parentColumns = ["clubId"],
            childColumns = ["clubId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["memberId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["clubId"]),
        Index(value = ["memberId"])
    ]
)
data class ClubMember (
    val clubId: Int,
    val memberId: Int,
)