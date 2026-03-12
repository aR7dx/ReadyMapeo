package com.readymapeo.mobile.ui.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.readymapeo.mobile.config.ApiConfig
import com.readymapeo.mobile.network.NetworkImage
import com.readymapeo.mobile.ui.component.Divider

@Composable
fun MobileCard(
    imagePath: String? = null,
    alternativeImage: @Composable () -> Unit,
    title: String,
    buttonText: String = "VOIR LES DETAILS",
    onclick: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = onclick
    ) {

        CardImage(
            imagePath = imagePath,
            alternativeImage = alternativeImage
        )

        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // title of the card
            CardTitle(title)

            // content of the card
            content()

            Divider()

            // see details button
            SeeDetailsButton(buttonText, onclick)
        }
    }
}

@Composable
fun CardImage(imagePath: String?, alternativeImage: (@Composable () -> Unit)? = null) {
    NetworkImage(
        modifier = Modifier.fillMaxWidth().height(200.dp),
        baseUrl = ApiConfig.BASE_URL_STORAGE,
        imagePath = imagePath,
        alternativeImage = alternativeImage,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun CardTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.displaySmall
    )
    Spacer(modifier = Modifier.height(2.dp))
}

@Composable
fun SeeDetailsButton(text: String = "", onclick: () -> Unit) {
    Button(
        onClick = onclick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(ApiConfig.COLOR_MAIN_BROWN)
        ),
        shape = RoundedCornerShape(6.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "arrow_right",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}