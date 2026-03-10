package com.readymapeo.mobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.readymapeo.mobile.R
import com.readymapeo.mobile.ui.screens.clubs.ClubsScreen
import com.readymapeo.mobile.ui.screens.home.HomeScreen
import com.readymapeo.mobile.ui.screens.profile.ProfileScreen
import com.readymapeo.mobile.ui.screens.races.RacesScreen
import com.readymapeo.mobile.ui.screens.raids.RaidsScreen
import com.readymapeo.mobile.ui.screens.raids.RaidScreen

val navRoutes = listOf(
    Route("/", { HomeScreen() }, R.drawable.home, "Home"),
    Route("/raids", { RaidsScreen() }, R.drawable.raids, "Raids"),
    Route("/races", { RacesScreen() }, R.drawable.races, "Courses"),
    Route("/clubs", { ClubsScreen() }, R.drawable.clubs, "Clubs"),
    Route("/profile", { ProfileScreen() }, R.drawable.account_avatar, "Profil")
)

val routes = navRoutes + listOf(
    Route("/raids/{id}", { RaidScreen() }),
    // ajout de nouvelles routes ici
)

class Route(
    val path: String,
    val screen: @Composable () -> Unit,
    val iconRes: Int? = null,
    val label: String? = "",
) {
    fun matches(path: String): Boolean {
        val routeParts = this.path.split("/")
        val pathParts = path.split("/")

        if (routeParts.size != pathParts.size) return false

        for (i in routeParts.indices) {
            if (!routeParts[i].startsWith("{") && routeParts[i] != pathParts[i]) {
                return false
            }
        }
        return true
    }

    fun extractParams(path: String): Map<String, String> {
        val routeParts = this.path.split("/")
        val pathParts = path.split("/")
        val params = mutableMapOf<String, String>()

        for (i in routeParts.indices) {
            if (routeParts[i].startsWith("{") && routeParts[i].endsWith("}")) {
                val paramName = routeParts[i].substring(1, routeParts[i].length - 1)
                params[paramName] = pathParts[i]
            }
        }
        return params
    }
}

val currentRoute = mutableStateOf(navRoutes[0])
var currentRouteParams: Map<String, String> = emptyMap()

fun redirectRoute(path: String) {
    val route = routes.find { it.matches(path) }
    route?.let {
        currentRoute.value = it
        currentRouteParams = it.extractParams(path)
    }
}


