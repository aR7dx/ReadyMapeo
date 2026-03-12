package com.readymapeo.mobile.utils

import org.json.JSONObject

fun JSONObject.optStringOrNull(key: String): String? {
    return optString(key, "").takeIf { it.isNotEmpty() && it != "null" }
}

fun JSONObject.optIntOrNull(key: String): Int? {
    return if (has(key) && !isNull(key)) optInt(key) else null
}

fun JSONObject.optBooleanOrNull(key: String): Boolean? {
    return if (has(key) && !isNull(key)) optBoolean(key) else null
}