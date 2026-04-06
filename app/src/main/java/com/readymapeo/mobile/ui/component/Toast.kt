package com.readymapeo.mobile.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ErrorToast(
    message: String,
    visible: Boolean,
    duration: Long = 3000,
    onDismiss: () -> Unit = {}
) {
    val isVisible = remember { mutableStateOf(visible) }

    LaunchedEffect(visible) {
        if (visible) {
            isVisible.value = true
            delay(duration)
            isVisible.value = false
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = isVisible.value,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEF4444), RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun SuccessToast(
    message: String,
    visible: Boolean,
    duration: Long = 3000,
    onDismiss: () -> Unit = {}
) {
    val isVisible = remember { mutableStateOf(visible) }

    LaunchedEffect(visible) {
        if (visible) {
            isVisible.value = true
            delay(duration)
            isVisible.value = false
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = isVisible.value,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF10B981), RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message,
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


