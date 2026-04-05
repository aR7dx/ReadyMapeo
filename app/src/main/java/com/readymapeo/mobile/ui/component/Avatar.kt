package com.readymapeo.mobile.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.readymapeo.mobile.ui.component.network.NetworkImage
import com.readymapeo.mobile.ui.theme.SemiBoldTypography

@Preview
@Composable
fun Avatar(
    modifier: Modifier = Modifier,
    baseUrl: String? = null,
    imagePath: String? = null,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    NetworkImage(
        modifier = modifier
            .size(50.dp)
            .clip(CircleShape),
        baseUrl = baseUrl,
        imagePath = imagePath,
        contentScale = contentScale,
        alternativeImage = {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4B8EF5)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = contentDescription
                        ?.trim()
                        ?.split(" ")
                        ?.filter { it.isNotEmpty() }
                        ?.map { it[0].uppercase() }
                        ?.joinToString("")
                    ?: "AZ",
                    style = SemiBoldTypography.headlineMedium,
                    color = Color.White
                )
            }
        },
    )
}

/*

baseUrl = ApiConfig.BASE_URL_STORAGE,
imagePath = user.value?.profilePhotoPath,
 */