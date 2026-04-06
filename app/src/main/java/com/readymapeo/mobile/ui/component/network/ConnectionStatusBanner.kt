package com.readymapeo.mobile.ui.component.network

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.readymapeo.mobile.network.NetworkConnectivity
import com.readymapeo.mobile.network.NetworkObserver
import com.readymapeo.mobile.network.NetworkStatusManager
import com.readymapeo.mobile.ui.theme.CtaMainGreen
import kotlinx.coroutines.delay

@Composable
fun ConnectionStatusBanner() {
    val isOnline by NetworkConnectivity.isOnline.collectAsState()
    var showConnectedBanner by remember { mutableStateOf(false) }
    var isInitialized by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val observer = object : NetworkObserver {
            override fun onNetworkStatusChanged(isOnline: Boolean) {}
        }
        NetworkStatusManager.addObserver(observer)

        onDispose {
            NetworkStatusManager.removeObserver(observer)
        }
    }

    LaunchedEffect(isOnline) {
        if (!isInitialized) {
            isInitialized = true
            showConnectedBanner = false
        } else if (isOnline && !showConnectedBanner) {
            showConnectedBanner = true
            delay(5000)
            showConnectedBanner = false
        }
    }

    if (!isOnline) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Vous êtes déconnecté (Mode: Hors-ligne)",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }

    if (isOnline) {
        AnimatedVisibility(
            visible = showConnectedBanner,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CtaMainGreen)
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Vous êtes connecté",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}


