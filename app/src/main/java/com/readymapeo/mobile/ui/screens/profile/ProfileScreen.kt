package com.readymapeo.mobile.ui.screens.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.routes.redirectRoute

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = viewModel()) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()


    if (!isLoggedIn) {
        redirectRoute("/login")
    }

    Text("Profil")
}