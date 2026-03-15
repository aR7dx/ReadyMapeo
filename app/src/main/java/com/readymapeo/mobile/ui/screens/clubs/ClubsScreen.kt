package com.readymapeo.mobile.ui.screens.clubs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.R
import com.readymapeo.mobile.routes.redirectRoute
import com.readymapeo.mobile.ui.component.card.CardCaption
import com.readymapeo.mobile.ui.component.card.CityAndPostalCode
import com.readymapeo.mobile.ui.component.card.MobileCard
import com.readymapeo.mobile.ui.component.placeholder.CardImageTemplate

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

        items(viewModel.clubs.value){
            MobileCard(
                imagePath = it.clubImage,
                alternativeImage = { CardImageTemplate(
                    backgroundColor = Color(0xFFE2FCEF),
                    imageVector = ImageVector.vectorResource(R.drawable.clubs),
                    iconColor = Color(0xFF6EE7B7)
                ) },
                title = it.clubName,
                onclick = {
                    redirectRoute("/clubs/${it.clubId}")
                },
            ) {
                CityAndPostalCode(it.clubCity, it.clubPostalCode)

                CardCaption(it.createdBy.toString())
            }
        }
    }
}