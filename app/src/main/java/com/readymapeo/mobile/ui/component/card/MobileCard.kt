package com.readymapeo.mobile.ui.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.readymapeo.mobile.config.ApiConfig
import com.readymapeo.mobile.network.NetworkImage
import com.readymapeo.mobile.ui.component.Divider
import com.readymapeo.mobile.ui.component.CTAButton
import com.readymapeo.mobile.ui.theme.SemiBoldTypography

@Composable
fun MobileCard(
    imagePath: String? = null,
    alternativeImage: @Composable () -> Unit,
    title: String,
    buttonText: String = "VOIR LES DETAILS",
    showButtonIcon: Boolean = true,
    showCtaButton: Boolean = true,
    onclick: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp),
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

            if (showCtaButton) {
                Divider()

                // see details button
                SeeDetailsButton(buttonText, showButtonIcon, onclick)
            }
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
        style = SemiBoldTypography.displaySmall
    )
    Spacer(modifier = Modifier.height(2.dp))
}

@Composable
fun SeeDetailsButton(text: String = "", showIcon: Boolean, onclick: () -> Unit) {
    CTAButton(
        text = text,
        showIcon = showIcon,
        onclick = onclick
    )
}