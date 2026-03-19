package com.readymapeo.mobile.ui.screens.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.routes.redirectRoute
import com.readymapeo.mobile.ui.component.LoadingSpinner

@Composable
fun LogoutScreen(viewModel: LogoutViewModel = viewModel()) {
    val isLoggedOut by viewModel.isLoggedOut.collectAsState()

    LaunchedEffect(isLoggedOut) {
        if (isLoggedOut == true) {
            redirectRoute("/")
        }
    }

    LoadingSpinner()
}

