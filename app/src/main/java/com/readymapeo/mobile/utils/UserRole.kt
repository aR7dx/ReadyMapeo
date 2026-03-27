package com.readymapeo.mobile.utils

enum class UserRole(val roleName: String) {
    ADMIN("admin"),
    ADHERENT("adherent"),
    RESPONSABLE_CLUB("responsable-club"),
    GESTIONNAIRE_RAID("gestionnaire-raid");

    companion object {
        fun fromString(value: String): UserRole? {
            return entries.find { it.roleName == value }
        }
    }
}