package com.readymapeo.mobile.navigation

import androidx.compose.runtime.Composable
import com.readymapeo.mobile.R
import com.readymapeo.mobile.ui.screens.home.HomeScreen
import com.readymapeo.mobile.ui.screens.profile.ProfileScreen
import com.readymapeo.mobile.ui.screens.races.RacesScreen
import com.readymapeo.mobile.ui.screens.raids.RaidsScreen

enum class AppDestinations(
    val label: String,
    val iconRes: Int,
    val destination : @Composable () -> Unit
) {
    HOME(
        label = "Accueil",
        iconRes = R.drawable.home,
        destination = { HomeScreen() }
    ),
    RAIDS(
        label = "Raids",
        iconRes = R.drawable.raids,
        destination = { RaidsScreen() }
    ),
    RACES(
        label = "Courses",
        iconRes = R.drawable.races,
        destination = { RacesScreen() }
    ),
    PROFILE(
        label = "Profil",
        iconRes = R.drawable.account_avatar,
        destination = { ProfileScreen() }
    ),
}