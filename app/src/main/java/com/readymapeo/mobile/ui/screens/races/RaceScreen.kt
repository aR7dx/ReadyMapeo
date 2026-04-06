package com.readymapeo.mobile.ui.screens.races

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.routes.currentRouteParams
import com.readymapeo.mobile.routes.redirectRoute
import com.readymapeo.mobile.ui.component.navigation.BackNavbar

@Composable
fun RaceScreen(viewModel: RaceViewModel = viewModel()) {

    val raceId = currentRouteParams["id"]?.toIntOrNull()
    val race = viewModel.race.value

    LaunchedEffect(raceId) {
        if (raceId != null) {
            viewModel.loadRace(raceId)
        }
    }

    if (race == null) {
        return
    }

    LazyColumn(
        modifier = Modifier
            .background(Color(0xFFF6F7F9))
            .fillMaxSize()
            .fillMaxHeight()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            BackNavbar { redirectRoute("/races") }
        }
    }

}