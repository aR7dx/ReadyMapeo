package com.readymapeo.mobile.ui.component.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.readymapeo.mobile.routes.currentRoute
import com.readymapeo.mobile.routes.navRoutes
import com.readymapeo.mobile.routes.redirectRoute

@PreviewScreenSizes
@Composable
fun NavigationBottomBar() {
    val currentTab by currentRoute

    NavigationSuiteScaffold(
        navigationSuiteItems = {
        navRoutes.forEach {
            item(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(it.iconRes!!),
                        contentDescription = it.label
                    )
                },
                label = { Text(it.label!!) },
                selected = it == currentTab,
                onClick = {
                    redirectRoute(it.path)
                }
            )
        }
    }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                currentTab.screen()
            }
        }
    }
}