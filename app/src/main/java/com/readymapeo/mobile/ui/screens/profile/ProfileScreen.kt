package com.readymapeo.mobile.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.R
import com.readymapeo.mobile.config.ApiConfig
import com.readymapeo.mobile.data.local.entity.User
import com.readymapeo.mobile.network.NetworkImage
import com.readymapeo.mobile.routes.redirectRoute
import com.readymapeo.mobile.ui.component.CTAButton
import com.readymapeo.mobile.ui.theme.BoldTypography
import com.readymapeo.mobile.utils.toFrenchDate
import com.readymapeo.mobile.utils.toTimestamp
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = viewModel()) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    when (isLoggedIn) {
        null -> {
            LoadingScreen()
        }
        false -> {
            LaunchedEffect(Unit) {
                redirectRoute("/login")
            }
        }
        true -> {
            ProfileContent(viewModel.user)
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
fun ProfileContent(userState: StateFlow<User?>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ProfileHeader(userState)
        }

        item {
            ProfileInfo(userState)
        }
    }
}


@Composable
fun ProfileHeader(userState: StateFlow<User?>) {
    val user = userState.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 8.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(24.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                NetworkImage(
                    modifier = Modifier
                        .size(100.dp),
                    baseUrl = ApiConfig.BASE_URL_STORAGE,
                    imagePath = user.value?.profilePhotoPath,
                    alternativeImage = {},
                    contentScale = ContentScale.Crop
                )

                Column {
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (user.value?.isPublic == false) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = ImageVector.vectorResource(R.drawable.lock_person),
                                contentDescription = "lock_icon"
                            )
                        }

                        println("creation du compte : ${user.value?.createdAt?.toTimestamp()}")
                        println("date actuelle : ${System.currentTimeMillis()}")

                        Text(
                            text = user.value?.name ?: "<Inconnu>",
                            style = BoldTypography.headlineLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Inscrit le ${user.value?.createdAt?.toFrenchDate() ?: "<Inconnu>"}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Age : ${(System.currentTimeMillis() - (user.value?.createdAt?.toTimestamp() ?: 0)).toFrenchDate()}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Téléphone : ${user.value?.phone}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        CTAButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "MODIFIER",
            backgroundColor = Color(0xFF1F2937),
            icon = ImageVector.vectorResource(R.drawable.edit_square),
            iconColor = Color.White,
            iconOnLeft = true
        ) {
            println("DEBUG: rediriger vers /profile/edit")
        }

        CTAButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "SE DÉCONNECTER",
            backgroundColor = Color(0xFFDC2626),
            icon = ImageVector.vectorResource(R.drawable.edit_square),
            iconColor = Color.White,
            iconOnLeft = true
        ) {
            println("DEBUG: rediriger vers /logout")
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ProfileInfo(userState: StateFlow<User?>) {
    val user = userState.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text("Licence: ${user.value?.licenseNumber}")
        }
    }
}