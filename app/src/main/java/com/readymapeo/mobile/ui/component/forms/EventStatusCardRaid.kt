package com.readymapeo.mobile.ui.component.forms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.readymapeo.mobile.R
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.ui.component.Divider
import com.readymapeo.mobile.ui.component.IconBox
import com.readymapeo.mobile.utils.toFrenchDate
import com.readymapeo.mobile.utils.toTimestamp

@Composable
fun EventStatusCardRaid(raid: Raid?) {
    Card(
        modifier = Modifier.padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.timer),
                    contentDescription = "timer",
                    tint = Color(0xFF5593F7)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "STATUT DE L'ÉVÉNEMENT",
                    color = Color(0xFF1E3A8A),
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Column(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EventInfoBloc("INSCRIPTIONS", raid?.insStartDate ?: "<Inconnu>")
                EventInfoBloc("FIN INSCRIPTIONS", raid?.insEndDate ?: "<Inconnu>")
                EventInfoBloc("ÉVÉNEMENT", raid?.raidDateStart ?: "<Inconnu>")
            }

        }


        Divider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))

        EventLocationCard(raid?.raidCity ?: "<Inconnu>")

    }
}

@Composable
fun EventInfoBloc(upperText: String, lowerText: String) {

    var color = Color(0xFF2563EB)
    var icon = ImageVector.vectorResource(R.drawable.target_circle)

    // événement terminé
    if (System.currentTimeMillis() > lowerText.toTimestamp()) {
        color = Color(0xFF10B981)
        icon = ImageVector.vectorResource(R.drawable.checked_circle)
    }

    val text =  lowerText.toFrenchDate()

    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

        IconBox(
            icon = icon,
            iconColor = Color.White,
            iconPadding = 0.dp,
            backgroundColor = color,
            radius = 99.dp
        )

        Column {
            Text(
                text = upperText,
                color = color,
                style = MaterialTheme.typography.titleSmall,
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
fun EventLocationCard(location: String) {
    Card(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7FAFF)),
        border = BorderStroke(1.dp, Color(0xFFE9F2FE))
    ) {
        Row(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconBox(
                icon = ImageVector.vectorResource(R.drawable.location_pin),
                iconColor = Color.White,
                backgroundColor = Color(0xFF2563EB),
                radius = 12.dp
            )

            Column {
                Text(
                    text = "LIEU DU RAID",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = location,
                    style = MaterialTheme.typography.titleMedium,
                )
            }


        }
    }
}