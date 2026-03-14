package com.readymapeo.mobile.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.R
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.routes.redirectRoute
import com.readymapeo.mobile.ui.component.Carousel
import com.readymapeo.mobile.ui.component.card.CityAndPostalCode
import com.readymapeo.mobile.ui.component.card.MobileCard
import com.readymapeo.mobile.ui.component.card.StartAndEndDate
import com.readymapeo.mobile.ui.component.form.RaidsFilterForm
import com.readymapeo.mobile.ui.component.placeholder.RaidImageTemplate
import com.readymapeo.mobile.ui.theme.CtaMainGreen
import com.readymapeo.mobile.ui.theme.CtaMainLightGreen
import com.readymapeo.mobile.ui.theme.RaidGreenOverlay

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val last3raids = viewModel.raids.value

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentPadding = PaddingValues(top = 0.dp, start = 0.dp, end = 0.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(575.dp)
            ) {
                HomeBannerImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(575.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(RaidGreenOverlay)
                )

                HomeBanner()
            }
        }

        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Raids ajoutés récemment",
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Découvrez les derniers raids créés.",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(12.dp))

                RaidCarousel(raidList = last3raids)

                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = { redirectRoute("/raids") },
                    ) {
                        Text(
                            text = "Voir tout le calendrier",
                            color = CtaMainLightGreen,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = CtaMainLightGreen
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun HomeBannerImage(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.hero),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun HomeBanner() {
    Column(modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp)) {
        Text(
            text = "Trouvez votre prochaine",
            color = Color.White,
            style = MaterialTheme.typography.displayMedium
        )
        Text(
            text = "Course d'Orientation",
            color = CtaMainLightGreen,
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Explorez et inscrivez-vous à des courses d'orientation.",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.height(32.dp))

        RaidsFilterForm(
            submitButtonColor = CtaMainGreen,
            backgroundColor = Color(0xE1EAF8F2),
            dividerColor = Color.LightGray
        ) { filterData ->
            redirectRoute("/raids")
        }
    }
}


@Composable
fun RaidCarousel(raidList: List<Raid>) {
    val raidsList = remember(raidList) {
        raidList.map { raid ->
            @Composable
            {
                MobileCard(
                    imagePath = raid.raidImage,
                    alternativeImage = { RaidImageTemplate(height = 200.dp) },
                    title = raid.raidName,
                    onclick = { redirectRoute("/raids/${raid.raidId}") }
                ) {
                    CityAndPostalCode(
                        locationName = raid.raidCity,
                    )

                    StartAndEndDate(
                        startDate = raid.raidDateStart,
                    )
                }
            }
        }
    }

    Carousel(carouselItems = raidsList)
}