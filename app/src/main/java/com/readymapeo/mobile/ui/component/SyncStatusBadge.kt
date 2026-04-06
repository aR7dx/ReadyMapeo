package com.readymapeo.mobile.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Badge pour afficher le statut de synchronisation d'un raid
 *
 * @param isSynced true si le raid est synchronisé, false si hors ligne
 */
@Composable
fun SyncStatusBadge(isSynced: Boolean) {
    if (isSynced) {
        Box(
            modifier = Modifier
                .background(Color(0xFFD1FAE5), RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "✓ Synchronisé",
                    color = Color(0xFF059669),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .background(Color(0xFFFEF3C7), RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "◯ Hors ligne",
                    color = Color(0xFFD97706),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}



