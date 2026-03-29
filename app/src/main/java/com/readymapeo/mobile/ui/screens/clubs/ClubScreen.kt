package com.readymapeo.mobile.ui.screens.clubs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.R
import com.readymapeo.mobile.config.ApiConfig
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.data.local.entity.User
import com.readymapeo.mobile.routes.currentRouteParams
import com.readymapeo.mobile.routes.redirectRoute
import com.readymapeo.mobile.ui.component.Avatar
import com.readymapeo.mobile.ui.component.CTAButton
import com.readymapeo.mobile.ui.component.Divider
import com.readymapeo.mobile.ui.component.card.CityAndPostalCode
import com.readymapeo.mobile.ui.component.card.LockedContentCard
import com.readymapeo.mobile.ui.component.card.MobileCard
import com.readymapeo.mobile.ui.component.card.StartAndEndDate
import com.readymapeo.mobile.ui.component.navigation.BackNavbar
import com.readymapeo.mobile.ui.component.template.ClubImageTemplate
import com.readymapeo.mobile.ui.component.template.ErrorPills
import com.readymapeo.mobile.ui.component.template.InfoPills
import com.readymapeo.mobile.ui.component.template.SuccessPills
import com.readymapeo.mobile.ui.theme.BoldTypography
import com.readymapeo.mobile.ui.theme.SemiBoldTypography
import com.readymapeo.mobile.utils.toTimestamp
import kotlin.text.toIntOrNull

@Composable
fun ClubScreen(viewModel: ClubViewModel = viewModel()) {

    val clubId = currentRouteParams["id"]?.toIntOrNull()
    val club = viewModel.club.value
    val isMember = viewModel.isMember.collectAsState()

    LaunchedEffect(clubId) {
        if (clubId != null) {
            viewModel.loadClub(clubId)
            viewModel.loadClubRaids(clubId)
            viewModel.loadClubMembers(clubId)
        }
    }

    if (club == null) {
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
            BackNavbar { redirectRoute("/clubs") }
        }

        item {
            MobileCard(
                imagePath = club.clubImage,
                alternativeImage = { ClubImageTemplate() },
                title = club.clubName,
                buttonText = "REJOINDRE LE CLUB",
                showButtonIcon = false,
                onclick = {}
            ) {
                CityAndPostalCode(
                    locationName = "${club.clubStreet}, ${club.clubCity}",
                    locationPostalCode = club.clubPostalCode,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.people),
                        contentDescription = null,
                        tint = Color(0xFFB6BBC4)
                    )
                    Text(
                        text = "${viewModel.clubMembers.value.size} membres",
                        color = Color(0xFF757d87),
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(
                        text = "IDENTIFIANT FFCO",
                        style = BoldTypography.bodyMedium,
                        color = Color(0xFF767C89)
                    )
                    Text(
                        text = club.ffsoId
                    )
                }
            }
        }

        item {
            if (viewModel.clubRaids.value.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(3.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Raids du club",
                            style = SemiBoldTypography.headlineLarge,
                        )

                        viewModel.clubRaids.value.forEach { clubRaid ->
                            ClubRaid(clubRaid)
                        }
                    }
                }
            }
        }

        item {
            if (isMember.value) {
                ClubMembers(viewModel.clubMembers.value)
            } else {
                LockedContentCard("Rejoignez ce club pour voir les membres et participer aux activités du club")
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ClubRaid(clubRaid: Raid?) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
        elevation = CardDefaults.cardElevation(1.dp),
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = clubRaid?.raidName ?: "<Nom introuvable>",
                    style = SemiBoldTypography.headlineSmall
                )
                ClubRaidPills(
                    raidDateStart = clubRaid?.raidDateStart,
                    raidDateEnd = clubRaid?.raidDateEnd
                )
            }
            StartAndEndDate(
                startDate = clubRaid?.raidDateStart ?: "<Date inconnue>",
            )
            ClubRaidRaceNumber(
                raceNumber = clubRaid?.racesCount
            )
            Spacer(modifier = Modifier.height(8.dp))
            CTAButton(
                text = "DETAILS & INSCRIPTION",
                textColor = Color(0xFF374151),
                backgroundColor = Color.White,
                border = BorderStroke(1.dp, Color(0xFFD1D5DB)),
                showIcon = false
            ) {
                redirectRoute("/raids/${clubRaid?.raidId}")
            }
        }
    }
}

@Composable
fun ClubRaidPills(raidDateStart: String?, raidDateEnd: String?) {
    raidDateStart?.toTimestamp()?.let { it ->
        if (it > System.currentTimeMillis()) {
            InfoPills(
                text = "A VENIR",
                textStyle = SemiBoldTypography.labelLarge
            )
        }
        else raidDateEnd?.toTimestamp()?.let {
            if (it < System.currentTimeMillis()) {
                ErrorPills(
                    text = "TERMINE",
                    textStyle = SemiBoldTypography.labelLarge
                )
            } else {
                SuccessPills(
                    text = "EN COURS",
                    textStyle = SemiBoldTypography.labelLarge
                )
            }
        }
    }
}

@Composable
fun ClubRaidRaceNumber(raceNumber: Int? = 0) {
    Row (verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.calendar),
            contentDescription = "calendar",
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$raceNumber épreuves disponibles",
            color = Color(0xFF7F8690)
        )
    }
}

@Composable
fun ClubMembers(members: List<User>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp),
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Membres (${members.size})",
                style = SemiBoldTypography.headlineLarge,
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                shape = RoundedCornerShape(12.dp)
            ) {
                members.forEachIndexed { index, member ->
                    MemberItem(member)

                    if (index < members.lastIndex) {
                        Divider(color = Color(0xFFE5E7EB))
                    }
                }
            }
        }
    }
}


@Composable
fun MemberItem(member: User) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Avatar(
            modifier = Modifier,
            baseUrl = ApiConfig.BASE_URL_STORAGE,
            imagePath = member.profilePhotoPath,
            contentDescription = member.name,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = member.name,
                style = BoldTypography.bodyLarge
            )
            Text(
                text = member.email,
            )
        }
    }
}