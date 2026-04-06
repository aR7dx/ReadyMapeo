package com.readymapeo.mobile.ui.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.readymapeo.mobile.config.ApiConfig
import com.readymapeo.mobile.ui.component.network.NetworkImage
import com.readymapeo.mobile.ui.component.Divider
import com.readymapeo.mobile.ui.component.CTAButton
import com.readymapeo.mobile.ui.theme.CtaMainBrown
import com.readymapeo.mobile.ui.theme.SemiBoldTypography

@Composable
fun MobileCard(
    modifier: Modifier = Modifier,
    baseUrl: String = ApiConfig.BASE_URL_STORAGE,
    imagePath: String? = null,
    alternativeImage: @Composable () -> Unit,
    status: @Composable () -> Unit = {},
    title: String,
    titleColor: Color = Color.Black,
    subTitle: String? = null,
    subTitleColor: Color? = null,
    titleBadge: @Composable () -> Unit = {},
    buttonText: String = "VOIR LES DETAILS",
    showButtonIcon: Boolean = true,
    showCtaButton: Boolean = true,
    ctaButtonBackgroundColor: Color = CtaMainBrown,
    onclick: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp),
        onClick = onclick
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            CardImage(
                baseUrl = baseUrl,
                imagePath = imagePath,
                alternativeImage = alternativeImage
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.TopStart
            ) {
                status()
            }
        }

        Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            // title of the card
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardTitle(
                    title = title,
                    titleColor = titleColor,
                    subTitle = subTitle,
                    subTitleColor = subTitleColor
                )

                titleBadge()
            }

            Spacer(modifier = Modifier.height(2.dp))

            // content of the card
            content()

            if (showCtaButton) {
                Divider()

                // see details button
                SeeDetailsButton(
                    text = buttonText,
                    backgroundColor = ctaButtonBackgroundColor,
                    showIcon = showButtonIcon,
                    onclick = onclick
                )
            }
        }
    }
}

@Composable
fun CardImage(baseUrl: String?, imagePath: String?, alternativeImage: (@Composable () -> Unit)? = null) {
    NetworkImage(
        modifier = Modifier.fillMaxWidth().height(200.dp),
        baseUrl = baseUrl,
        imagePath = imagePath,
        alternativeImage = alternativeImage,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun CardTitle(
    title: String,
    titleColor: Color,
    subTitle: String? = null,
    subTitleColor: Color? = null
) {
    Column {
        Text(
            text = title,
            color = titleColor,
            style = SemiBoldTypography.displaySmall
        )

        subTitle?.let { subTitle ->
            Text(
                text = subTitle,
                color = subTitleColor ?: titleColor,
                style = SemiBoldTypography.bodyMedium
            )
        }
    }
}

@Composable
fun SeeDetailsButton(
    text: String = "",
    backgroundColor: Color,
    showIcon: Boolean,
    onclick: () -> Unit
) {
    CTAButton(
        text = text,
        backgroundColor = backgroundColor,
        showIcon = showIcon,
        onclick = onclick
    )
}