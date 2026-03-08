package com.readymapeo.mobile.ui.screens.raids

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RaidsScreen(viewModel: RaidsViewModel = viewModel()) {

    LaunchedEffect(Unit) {
        viewModel.loadRaids()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        viewModel.raids.value.forEach {

            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = {}
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row() {
                        Badge { Text("RAID") }
                        Text(it.raidDateStart)
                    }
                    Text(it.raidName)
                    Text(it.raidCity)
                }
            }
        }
    }

}