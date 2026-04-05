package com.readymapeo.mobile.ui.component.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL


@Composable
fun NetworkImage(
    modifier: Modifier,
    baseUrl: String? = null,
    imagePath: String? = null,
    alternativeImage: (@Composable () -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(baseUrl, imagePath) {

        if (baseUrl.isNullOrBlank() || imagePath.isNullOrBlank()) {
            bitmap = null
        }
        else {
            bitmap = withContext(Dispatchers.IO) {
                try {
                    val stream = URL("$baseUrl/$imagePath").openStream()
                    BitmapFactory.decodeStream(stream)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale
        )
    } else if (alternativeImage != null) {
        alternativeImage()
    }
}