package com.readymapeo.mobile.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

// String -> 31 Décembre 2026
fun String.toFrenchDate(): String {
    val instant = Instant.parse(this)
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH)

    return formatter.format(instant.atZone(ZoneId.systemDefault()))
}


// Long -> 31 Décembre 2026
fun Long.toFrenchDate(): String {
    val instant = Instant.ofEpochMilli(this)
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH)

    return formatter.format(instant.atZone(ZoneId.systemDefault()))
}


// String -> 31 Décembre
fun String.toFrenchDateNoYear(): String {
    val instant = Instant.parse(this)
    val formatter = DateTimeFormatter.ofPattern("d MMMM", Locale.FRENCH)

    return formatter.format(instant.atZone(ZoneId.systemDefault()))
}


// Long -> 31 Décembre
fun Long.toFrenchDateNoYear(): String {
    val instant = Instant.ofEpochMilli(this)
    val formatter = DateTimeFormatter.ofPattern("d MMMM", Locale.FRENCH)

    return formatter.format(instant.atZone(ZoneId.systemDefault()))
}


fun String.toTimestamp(): Long {
    val instant = Instant.parse(this)
    return instant.toEpochMilli()
}