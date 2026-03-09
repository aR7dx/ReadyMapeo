package com.readymapeo.mobile.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.toFrenchDate(): String {
    val instant = Instant.parse(this)

    val formatter = DateTimeFormatter.ofPattern(
        "d MMMM yyyy", Locale.FRENCH
    )

    return formatter.format(
        instant.atZone(ZoneId.systemDefault())
    )
}