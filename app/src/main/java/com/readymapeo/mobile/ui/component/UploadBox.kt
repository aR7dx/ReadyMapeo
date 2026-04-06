package com.readymapeo.mobile.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun UploadBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .border(2.dp, Color.Gray, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Cliquez pour télécharger")
            Text("ou glissez-déposez", style = MaterialTheme.typography.bodySmall)
            Text("PNG, JPG, GIF jusqu'à 5MB", style = MaterialTheme.typography.bodySmall)
        }
    }
}