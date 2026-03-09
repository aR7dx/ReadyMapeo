package com.readymapeo.mobile.ui.screens.raids

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.R
import com.readymapeo.mobile.config.ApiConfig
import com.readymapeo.mobile.utils.NetworkImage
import com.readymapeo.mobile.utils.toFrenchDate

@Composable
fun RaidsScreen(viewModel: RaidsViewModel = viewModel()) {

    LaunchedEffect(Unit) {
        viewModel.loadRaids()
    }

    Column(
        modifier = Modifier.padding(16.dp).fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Tout les raids",
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = "Découvrez tous les raids disponibles et inscrivez-vous à l'aventure",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF4B5563),
            )
        }

        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            items(viewModel.raids.value) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp),
                    onClick = {}
                ) {

                    RaidImage(it.raidImage)

                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        // raid name
                        RaidName(it.raidName)

                        // location of raids
                        CityAndPostalCode(it.raidCity, it.raidPostalCode)

                        // dates of raid
                        StartAndEndDate(it.raidDateStart, it.raidDateEnd)

                        // club of the raid
                        HostClub(it.club.clubName)
                    }
                }
            }
        }
    }
}

@Composable
fun RaidImage(raidImage: String) {
    NetworkImage(
        imageUrl = "${ApiConfig.BASE_URL_STORAGE}/${raidImage}",
        modifier = Modifier.fillMaxWidth().height(200.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun RaidName(raidName: String) {
    Text(
        text = raidName,
        style = MaterialTheme.typography.displaySmall
    )
}

@Composable
fun CityAndPostalCode(raidCity: String, raidPostalCode: String) {
    Row (verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.location_pin),
            contentDescription = "location_pin"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("$raidCity $raidPostalCode")
    }
}

@Composable
fun StartAndEndDate(raidDateStart: String, raidDateEnd: String) {
    Row (verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.calendar),
            contentDescription = "calendar"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("${raidDateStart.toFrenchDate()} - ${raidDateEnd.toFrenchDate()}")
    }
}

@Composable
fun HostClub(clubName: String) {
    Row (verticalAlignment = Alignment.CenterVertically) {
        Icon (
            imageVector = ImageVector.vectorResource(R.drawable.building),
            contentDescription = "organizer club"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(clubName)
    }
}