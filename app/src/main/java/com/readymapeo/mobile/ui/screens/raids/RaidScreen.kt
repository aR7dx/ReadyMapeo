package com.readymapeo.mobile.ui.screens.raids

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

    LaunchedEffect(raidId) {
        if (raidId != null) {
            viewModel.loadRaid(raidId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { redirectRoute("/raids") }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Retour"
                )
            }
            Text(
                text = "Détails du raid",
                style = MaterialTheme.typography.displaySmall
            )
        }

        if (viewModel.raid.value != null) {
            Text(
                text = viewModel.raid.value!!.raidName,
                style = MaterialTheme.typography.displayMedium
            )
        } else {
            Text("Chargement...")
        }
    }
}