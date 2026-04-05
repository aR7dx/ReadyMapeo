package com.readymapeo.mobile

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.readymapeo.mobile.manager.AuthManager
import com.readymapeo.mobile.data.local.AppDatabase
import com.readymapeo.mobile.manager.TokenManager
import com.readymapeo.mobile.manager.RolesManager
import com.readymapeo.mobile.data.repository.AuthRepository
import com.readymapeo.mobile.data.repository.ClubMemberRepository
import com.readymapeo.mobile.data.repository.RaidRepository
import com.readymapeo.mobile.data.repository.ClubRepository
import com.readymapeo.mobile.data.repository.RaceRepository
import com.readymapeo.mobile.data.repository.UserRepository
import com.readymapeo.mobile.manager.DataStoreProvider
import com.readymapeo.mobile.network.ApiClient
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
    val appContext = context.applicationContext as Application
    DataStoreProvider.init(appContext)

    NetworkConnectivity.init(appContext)

    TokenManager.init()
    RolesManager.init()
    ApiClient.init(appContext)
    AuthManager.init()

    val database = AppDatabase.getInstance(appContext)
    AuthRepository.setDatabase(database)
    UserRepository.setDatabase(database)
    ClubMemberRepository.setDatabase(database)
    RaidRepository.setDatabase(database)
    ClubRepository.setDatabase(database)
    RaceRepository.setDatabase(database)

    SyncManager.init(appContext)

    NavigationBottomBar()
}