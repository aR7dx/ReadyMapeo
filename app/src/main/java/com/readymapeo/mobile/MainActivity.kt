package com.readymapeo.mobile

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.data.local.token.TokenManager
import com.readymapeo.mobile.data.repository.RaidRepository
import com.readymapeo.mobile.data.repository.ClubRepository
import com.readymapeo.mobile.sync.SyncManager
import com.readymapeo.mobile.ui.theme.ReadyMapeoTheme
import com.readymapeo.mobile.ui.component.navigation.NavigationBottomBar
import com.readymapeo.mobile.network.NetworkConnectivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ReadyMapeoTheme {
                ReadyMapeoApp(context = this)
            }
        }
    }
}

@Composable
fun ReadyMapeoApp(context: Context) {

    NetworkConnectivity.init(context)
    SyncManager.initSyncWorker(context)

    TokenManager.init(context)

    val database = AppDatabase.getInstance(context)
    RaidRepository.setDatabase(database)
    ClubRepository.setDatabase(database)

    NavigationBottomBar()
}