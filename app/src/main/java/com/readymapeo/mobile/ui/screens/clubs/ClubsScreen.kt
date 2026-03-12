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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.ui.component.CardCaption
import com.readymapeo.mobile.ui.component.CityAndPostalCode
import com.readymapeo.mobile.ui.component.MobileCard

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
                title = it.clubName,
                onclick = {},
            ) {
                CityAndPostalCode(it.clubCity, it.clubPostalCode)

                CardCaption(it.createdBy.toString())
            }
        }
    }
}