package com.readymapeo.mobile.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.readymapeo.mobile.R
import com.readymapeo.mobile.ui.screens.auth.LoginScreen
import com.readymapeo.mobile.ui.screens.auth.LogoutScreen
import com.readymapeo.mobile.ui.screens.clubs.ClubScreen
import com.readymapeo.mobile.ui.screens.clubs.ClubsScreen
import com.readymapeo.mobile.ui.screens.home.HomeScreen
import com.readymapeo.mobile.ui.screens.profile.ProfileScreen
import com.readymapeo.mobile.ui.screens.races.RaceScreen
import com.readymapeo.mobile.ui.screens.races.RacesScreen
import com.readymapeo.mobile.ui.screens.raids.CreateRaidScreen
import com.readymapeo.mobile.ui.screens.raids.RaidsScreen
import com.readymapeo.mobile.ui.screens.raids.RaidScreen

val navRoutes = listOf(
    Route("/", { HomeScreen() }, R.drawable.home, "Accueil"),
    Route("/raids", { RaidsScreen() }, R.drawable.raids, "Raids"),
    Route("/races", { RacesScreen() }, R.drawable.races, "Courses"),
    Route("/clubs", { ClubsScreen() }, R.drawable.clubs, "Clubs"),
    Route("/profile", { ProfileScreen() }, R.drawable.account_avatar, "Profil")
)

val routes = navRoutes + listOf(
    Route("/raids/create", { CreateRaidScreen() }),
    Route("/login", { LoginScreen() }),
    Route("/logout", { LogoutScreen() }),
    // route avec paramètre après
    Route("/raids/{id}", { RaidScreen() }),
    Route("/races/{id}", { RaceScreen()}),
    Route("/clubs/{id}", { ClubScreen() }),
    // ajout de nouvelles routes ici
)

class Route (
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

    fun extractPathParams(path: String): Map<String, String> {
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
var currentQueryParams: Map<String, String> = emptyMap()

fun redirectRoute(path: String) {
    val pathAndQuery = path.split("?")
    val _path = pathAndQuery[0]
    
    val queryParams = mutableMapOf<String, String>()
    if (pathAndQuery.size > 1) {
        pathAndQuery[1].split("&").forEach { param ->
            val parts = param.split("=")
            if (parts.size == 2) {
                queryParams[parts[0]] = parts[1]
            }
        }
    }
    
    val route = routes.find { it.matches(_path) }
    route?.let {
        currentRoute.value = it
        currentRouteParams = it.extractPathParams(_path)
        currentQueryParams = queryParams
    }
}


