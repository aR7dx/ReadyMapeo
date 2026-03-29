package com.readymapeo.mobile.ui.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.readymapeo.mobile.R
import com.readymapeo.mobile.ui.theme.SemiBoldTypography

@Preview(showBackground = true)
@Composable
fun LockedContentCard(
    text: String = "Placeholder",
    textColor: Color = Color(0xFF415A9D),
    backgroundColor: Color = Color(0xFFEFF6FF),
    borderColor: Color = Color(0xFFBFDBFE),
    iconColor: Color = Color(0xFF2563EB)
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(3.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp, horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.locker),
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = text,
                color = textColor,
                style = SemiBoldTypography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}