package com.readymapeo.mobile.ui.screens.raids

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.readymapeo.mobile.routes.currentRouteParams
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.R
import com.readymapeo.mobile.routes.redirectRoute
import com.readymapeo.mobile.ui.component.Pills
import com.readymapeo.mobile.ui.component.navigation.BackNavbar
import com.readymapeo.mobile.ui.component.network.NetworkImage
import com.readymapeo.mobile.config.ApiConfig
import com.readymapeo.mobile.ui.component.card.MobileCard
import com.readymapeo.mobile.ui.component.placeholder.ErrorPills
import com.readymapeo.mobile.ui.component.placeholder.InfoPills
import com.readymapeo.mobile.ui.component.placeholder.NotContentDashedCard
import com.readymapeo.mobile.ui.component.placeholder.RaceImageTemplate
import com.readymapeo.mobile.ui.component.placeholder.RaidImageTemplate
import com.readymapeo.mobile.ui.component.placeholder.SuccessPills
import com.readymapeo.mobile.ui.component.forms.EventStatusCardRaid
import com.readymapeo.mobile.ui.theme.RaidGreenOverlay
import com.readymapeo.mobile.ui.theme.RaidGreenPrimary
import com.readymapeo.mobile.ui.theme.RaidGreenSecondary
import com.readymapeo.mobile.ui.theme.SemiBoldTypography
import com.readymapeo.mobile.utils.toFrenchDate
import com.readymapeo.mobile.utils.toHour
import com.readymapeo.mobile.utils.toTimestamp

@Composable
fun RaidScreen(viewModel: RaidViewModel = viewModel()) {
    val raidId = currentRouteParams["id"]?.toIntOrNull()
    val raid = viewModel.raid.value

    LaunchedEffect(raidId) {
        if (raidId != null) {
            viewModel.loadRaid(raidId)
            viewModel.loadRaidRaces(raidId)
        }
    }

    if (raid == null) {
        return
    }

    LazyColumn(
        modifier = Modifier
            .background(Color(0xFFF6F7F9))
            .fillMaxSize()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            RaidBanner(
                title = raid.raidName,
                clubName = raid.clubName ?: "<Inconnu>",
                raidLocation = raid.raidCity,
                racesCount = raid.racesCount.toString(),
                description = raid.raidDescription,
                imagePath = raid.raidImage
            )
        }

        item {
            EventStatusCardRaid(raid)
        }

        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "COURSES DISPONIBLES",
                    color = Color(0xFF1E3A8A),
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${viewModel.raidRaces.value.size} courses",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        items(viewModel.raidRaces.value) { race ->
            val isRaceFinished = race.raceDateEnd.toTimestamp() <= System.currentTimeMillis()

            MobileCard(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                baseUrl = ApiConfig.BASE_URL,
                imagePath = race.raceImageUrl,
                buttonText = if (isRaceFinished) {
                    "CONSULTER LES RÉSULTATS"
                } else {
                    "PLUS DE DETAILS"
               },
                alternativeImage = { RaceImageTemplate() },
                status = {
                    if (isRaceFinished) {
                        ErrorPills(
                            text = "TERMINÉE",
                            textStyle = SemiBoldTypography.bodySmall
                        )
                    }
                    else if (race.raceDateStart.toTimestamp() > System.currentTimeMillis()) {
                        InfoPills(
                            text = "À VENIR",
                            textStyle = SemiBoldTypography.bodySmall,
                        )
                    } else {
                        SuccessPills(
                            text = "EN COURS",
                            textStyle = SemiBoldTypography.bodySmall,
                        )
                    }
                },
                title = race.raceName,
                titleColor = Color(0xFF1E3A8A),
                subTitle = "ORGANISE PAR ${race.raceOrganizerName.uppercase()}",
                subTitleColor = Color(0xFFBBC5E7),
                titleBadge = { InfoPills(text = race.raceDifficulty) },
                ctaButtonBackgroundColor = if (isRaceFinished) {
                    Color(0xFF1E3A8A)
                } else {
                    Color(0xFF2563EB)
                },
                onclick = { redirectRoute("/races/${race.raceId}") }
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .border(BorderStroke(1.dp, Color(0xFFF3F4F6)), RoundedCornerShape(8.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "DATE & HEURE",
                                color = Color(0xFFC1C9DE),
                                style = SemiBoldTypography.bodyMedium
                            )
                            Text(
                                text = race.raceDateStart.toFrenchDate(),
                                color = Color(0xFF1E3A8A),
                                style = SemiBoldTypography.bodyMedium
                            )
                            Text(
                                text = race.raceDateStart.toHour(),
                                color = Color(0xFF059669),
                                style = SemiBoldTypography.bodyMedium
                            )
                        }
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(BorderStroke(1.dp, Color(0xFFF3F4F6)), RoundedCornerShape(8.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxHeight()
                        ) {
                            Text(
                                text = "DURÉE",
                                color = Color(0xFFC1C9DE),
                                style = SemiBoldTypography.bodyMedium
                            )
                            Text(
                                text = race.raceDurationMinutes.toString() + "MIN",
                                color = Color(0xFF1E3A8A),
                                style = SemiBoldTypography.bodyMedium
                            )
                        }
                    }
                }
            }
        }

        item {
            if (viewModel.raidRaces.value.isEmpty()) {
                Column(modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
                ) {
                    NotContentDashedCard(
                        title = "AUCUNE COURSE",
                        subText = "AUCUNE COURSE N'EST DISPONIBLE POUR CE RAID POUR LE MOMENT."
                    )
                }
            }
        }
    }
}


@Composable
fun RaidBanner(title: String, clubName: String, raidLocation: String, racesCount: String, description: String?, imagePath: String?) {
    Box(modifier = Modifier.fillMaxWidth()) {

        Box(modifier = Modifier.fillMaxWidth().height(250.dp)) {
            NetworkImage(
                modifier = Modifier.fillMaxWidth(),
                baseUrl = ApiConfig.BASE_URL_STORAGE,
                imagePath = imagePath,
                alternativeImage = { RaidImageTemplate() },
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(RaidGreenOverlay)
            )
        }

        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            BackNavbar(
                textColor = RaidGreenSecondary,
                iconColor = RaidGreenSecondary,
                onclick = { redirectRoute("/raids") }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    color = RaidGreenPrimary,
                    style = MaterialTheme.typography.displayMedium
                )
                Pills(
                    text = clubName,
                    textColor = Color(0xFF117F60),
                    textStyle = MaterialTheme.typography.titleSmall,
                    backgroundColor = Color(0xFFECFDF5),
                )
            }
            Row (verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Pills(
                    text = raidLocation,
                    textColor = RaidGreenSecondary,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    icon = ImageVector.vectorResource(R.drawable.location_pin),
                    iconColor = RaidGreenSecondary,
                    backgroundColor = Color.White.copy(alpha = 0.10f)
                )
                Pills(
                    text = "$racesCount Courses",
                    textColor = RaidGreenSecondary,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    icon = ImageVector.vectorResource(R.drawable.trophy),
                    iconColor = RaidGreenSecondary,
                    backgroundColor = Color.White.copy(alpha = 0.10f)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Row {
                Text(
                    text = description ?: "Pas de description",
                    color = RaidGreenSecondary,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}