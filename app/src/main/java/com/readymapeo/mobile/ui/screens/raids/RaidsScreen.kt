package com.readymapeo.mobile.ui.screens.raids

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.routes.redirectRoute
import com.readymapeo.mobile.ui.component.card.CardCaption
import com.readymapeo.mobile.ui.component.card.CityAndPostalCode
import com.readymapeo.mobile.ui.component.card.MobileCard
import com.readymapeo.mobile.ui.component.card.StartAndEndDate
import com.readymapeo.mobile.ui.component.placeholder.NotContentDashedCard
import com.readymapeo.mobile.ui.component.placeholder.RaidImageTemplate
import com.readymapeo.mobile.ui.component.form.CollapsibleRaidsFilterForm

@Composable
fun RaidsScreen(viewModel: RaidsViewModel = viewModel()) {
    val raids = viewModel.raids.value

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

        item {
            CollapsibleRaidsFilterForm(viewModel)
        }

        items(raids) { raid ->
            MobileCard(
                imagePath = raid.raidImage,
                alternativeImage = { RaidImageTemplate() },
                title = raid.raidName,
                onclick = { redirectRoute("/raids/${raid.raidId}") }
            ) {
                // location of raids
                CityAndPostalCode(raid.raidCity, raid.raidPostalCode)

                // dates of raid
                StartAndEndDate(raid.raidDateStart, raid.raidDateEnd)

                // caption of the card
                CardCaption(raid.clubName ?: "<Inconnu>")
            }
        }

        item {
            if (raids.isEmpty()) {
                NotContentDashedCard(
                    title = "Aucun raid disponible",
                    subText = "Il n'y a actuellement aucun raid disponible. Revenez bientôt pour découvrir de nouvelles aventures !"
                )
            }
        }
    }
}
