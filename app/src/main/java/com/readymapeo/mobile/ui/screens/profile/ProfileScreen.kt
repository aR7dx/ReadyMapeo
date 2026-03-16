package com.readymapeo.mobile.ui.screens.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.routes.redirectRoute

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = viewModel()) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    when (isLoggedIn) {
        null -> {
            LoadingScreen()
        }
        false -> {
            redirectRoute("/login")
        }
        true -> {
            ProfileContent()
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ProfileContent() {
    Text("Profil")
}