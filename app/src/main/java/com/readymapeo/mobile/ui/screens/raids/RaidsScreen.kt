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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.navigation.redirectRoute
import com.readymapeo.mobile.ui.component.CardCaption
import com.readymapeo.mobile.ui.component.CityAndPostalCode
import com.readymapeo.mobile.ui.component.MobileCard
import com.readymapeo.mobile.ui.component.StartAndEndDate
import com.readymapeo.mobile.ui.component.placeholder.CardImageTemplate
import com.readymapeo.mobile.R

@Composable
fun RaidsScreen(viewModel: RaidsViewModel = viewModel()) {


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
            MobileCard(
                imagePath = it.raidImage,
                alternativeImage = { CardImageTemplate(
                    backgroundColor = Color(0xFFE7ECFF),
                    imageVector = ImageVector.vectorResource(R.drawable.trophy),
                    iconColor =  Color(0xFFA5B4FC)
                )},
                title = it.raidName,
                onclick = { redirectRoute("/raids/${it.raidId}") }
            ) {
                // location of raids
                CityAndPostalCode(it.raidCity, it.raidPostalCode)

                // dates of raid
                StartAndEndDate(it.raidDateStart, it.raidDateEnd)

                CardCaption(it.cluId.toString())
            }
        }
    }
}