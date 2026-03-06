package com.readymapeo.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.readymapeo.mobile.ui.theme.ReadyMapeoTheme
import com.readymapeo.mobile.navigation.NavigationBottomBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReadyMapeoTheme {
                ReadyMapeoApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun ReadyMapeoApp() {
    NavigationBottomBar()
}