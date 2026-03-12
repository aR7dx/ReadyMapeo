package com.readymapeo.mobile.ui.screens.raids

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.readymapeo.mobile.navigation.currentRouteParams
import com.readymapeo.mobile.navigation.redirectRoute
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RaidScreen(viewModel: RaidViewModel = viewModel()) {
    val raidId = currentRouteParams["id"]?.toIntOrNull()
    val raid = viewModel.raid.value

    LaunchedEffect(raidId) {
        if (raidId != null) {
            viewModel.loadRaid(raidId)
        }
    }

    if (raid == null) {
        return
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BackNavbar()

        Text(
            text = raid.raidName,
            style = MaterialTheme.typography.displayMedium
        )
    }
}

@Composable
fun BackNavbar(text: String = "Retour") {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { redirectRoute("/raids") },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "back"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.displaySmall
        )
    }
}