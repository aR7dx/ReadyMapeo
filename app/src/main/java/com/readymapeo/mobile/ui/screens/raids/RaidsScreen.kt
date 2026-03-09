package com.readymapeo.mobile.ui.screens.raids

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Tous les raids",
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = "Découvrez tous les raids disponibles et inscrivez-vous à l'aventure",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        items(viewModel.raids.value) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp),
                onClick = {}
            ) {

                RaidImage(it.raidImage)

                Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // raid name
                    RaidName(it.raidName)

                    // location of raids
                    CityAndPostalCode(it.raidCity, it.raidPostalCode)

                    // dates of raid
                    StartAndEndDate(it.raidDateStart, it.raidDateEnd)

                    // club of the raid
                    HostClub(it.club.clubName)

                    Divider()

                    // see details
                    SeeDetailsButton("VOIR LES DETAILS")
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
    Spacer(modifier = Modifier.height(2.dp))
}

@Composable
fun CityAndPostalCode(raidCity: String, raidPostalCode: String) {
    Row (verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.location_pin),
            contentDescription = "location_pin",
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$raidCity $raidPostalCode",
            color = Color(0xFF757d87),
        )
    }
}

@Composable
fun StartAndEndDate(raidDateStart: String, raidDateEnd: String) {
    Row (verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.calendar),
            contentDescription = "calendar",
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${raidDateStart.toFrenchDate()} - ${raidDateEnd.toFrenchDate()}",
            color = Color(0xFF7F8690)
        )
    }
}

@Composable
fun HostClub(clubName: String) {
    Row (verticalAlignment = Alignment.CenterVertically) {
        Icon (
            imageVector = ImageVector.vectorResource(R.drawable.building),
            contentDescription = "organizer_club",
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = clubName,
            color = Color(0xFF7F8690)
        )
    }
}

@Composable
fun Divider() {
    Spacer(modifier = Modifier.height(4.dp))
    HorizontalDivider(
        color = Color(0xFFE6E6ED)
    )
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun SeeDetailsButton(text: String = "") {
    Button(
        onClick = {},
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(ApiConfig.COLOR_MAIN_BROWN)
        ),
        shape = RoundedCornerShape(6.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "arrow_right",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}