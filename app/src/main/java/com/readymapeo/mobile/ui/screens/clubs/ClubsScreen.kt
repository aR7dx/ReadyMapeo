package com.readymapeo.mobile.ui.screens.clubs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.routes.redirectRoute
import com.readymapeo.mobile.ui.component.CTAButton
import com.readymapeo.mobile.ui.component.Input
import com.readymapeo.mobile.ui.component.card.CardCaption
import com.readymapeo.mobile.ui.component.card.CityAndPostalCode
import com.readymapeo.mobile.ui.component.card.MobileCard
import com.readymapeo.mobile.ui.component.placeholder.ClubImageTemplate
import com.readymapeo.mobile.ui.component.placeholder.NotContentDashedCard

@Composable
fun ClubsScreen(viewModel: ClubsViewModel = viewModel()) {

    LaunchedEffect(Unit) {
        viewModel.loadClubs()
    }

    LazyColumn(
        modifier = Modifier.fillMaxHeight(),
        contentPadding = PaddingValues(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Tous les clubs",
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = "Découvrez et rejoignez des clubs de course d'orientation près de chez vous",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Input(
                    mutableValue = viewModel.clubSearch,
                    placeholder = "Rechercher des clubs...",
                    onValueChange = {
                        viewModel.filterClubs()
                    }
                )
            }
        }

        items(viewModel.clubs.value){
            MobileCard(
                imagePath = it.clubImage,
                alternativeImage = { ClubImageTemplate() },
                title = it.clubName,
                onclick = {
                    redirectRoute("/clubs/${it.clubId}")
                },
            ) {
                CityAndPostalCode(it.clubCity, it.clubPostalCode)

                CardCaption(it.creatorName ?: "<Membre introuvable>")
            }
        }

        item {
            if (viewModel.clubs.value.isEmpty()) {
                NotContentDashedCard(
                    title = "Aucun club trouvé",
                    titleColor = Color(0xFF111827),
                    subText = "Essayez de modifier votre recherche ou effacer les filtres pour voir tout les clubs.",
                    subTextColor = Color(0xFF5B6471)
                ) {
                    Spacer(modifier = Modifier.width(16.dp))
                    CTAButton(
                        modifier = Modifier.fillMaxWidth(0.6f),
                        text = "VOIR TOUT LES CLUBS",
                        onclick = {
                            viewModel.clubSearch.value = ""
                            viewModel.filterClubs()
                        }
                    )
                }
            }
        }
    }
}