package com.readymapeo.mobile.ui.screens.races

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.config.ApiConfig
import com.readymapeo.mobile.routes.redirectRoute
import com.readymapeo.mobile.ui.component.Input
import com.readymapeo.mobile.ui.component.card.CardCaption
import com.readymapeo.mobile.ui.component.card.CityAndPostalCode
import com.readymapeo.mobile.ui.component.card.MobileCard
import com.readymapeo.mobile.ui.component.card.StartAndEndDate

@Composable
fun RacesScreen(viewModel: RacesViewModel = viewModel()) {
    val races = viewModel.races.value

    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Calendrier des courses",
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = "Découvrez tous les prochaines épreuves d'orientation",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Input(
                    mutableValue = viewModel.raceSearch,
                    placeholder = "Rechercher une course par nom, lieu raid ou club...",
                    onValueChange = {
                        viewModel.filterRaces()
                    }
                )
            }
        }

        items(races) { race ->
            MobileCard(
                baseUrl = ApiConfig.BASE_URL,
                imagePath = race.raceImageUrl,
                alternativeImage = {},
                title = race.raceName,
                onclick = { redirectRoute("/races/${race.raceId}") }
            ) {
                // location of the race
                CityAndPostalCode(race.raceRaidCity, "???")

                // dates of the race
                StartAndEndDate(race.raceDateStart, race.raceDateEnd)

                // caption of the card
                CardCaption(race.raceRaidName, race.raceClubName)
            }
        }
    }

}