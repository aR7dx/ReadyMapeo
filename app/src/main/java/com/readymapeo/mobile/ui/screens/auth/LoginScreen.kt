package com.readymapeo.mobile.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.R
import com.readymapeo.mobile.data.api.AuthApiService
import com.readymapeo.mobile.routes.redirectRoute
import com.readymapeo.mobile.ui.component.Input
import com.readymapeo.mobile.ui.component.LabelledDivider
import com.readymapeo.mobile.ui.component.form.SubmitFormButton
import com.readymapeo.mobile.ui.theme.BoldTypography
import com.readymapeo.mobile.ui.theme.CtaMainLightGreen
import com.readymapeo.mobile.utils.InputType
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
    ) {
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }

        LogoAndText()

        LabelledDivider(label = "Ou continuer avec un e-mail")

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Input(
                mutableValue = email,
                label = "Email",
                placeholder = "Entrez votre email"
            )
            Input(
                type = InputType.PASSWORD,
                mutableValue = password,
                label = "Mot de passe",
                placeholder = "Entrez votre mot de passe"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SubmitLoginForm(
            viewModel = viewModel,
            email = email.value,
            password = password.value
        )
    }
}

@Composable
fun LogoAndText() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(75.dp),
                imageVector = ImageVector.vectorResource(R.drawable.logo_min),
                contentDescription = "logo_min_icon",
                contentScale = ContentScale.Fit
            )
        }

        Text(
            text = "Ravi de vous revoir",
            style = BoldTypography.displayMedium
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Ou continuer avec un e-mail",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = "créer un nouveau compte",
                style = MaterialTheme.typography.bodyMedium,
                color = CtaMainLightGreen
            ).toString()
        }
    }
}

@Composable
fun SubmitLoginForm(
    viewModel: LoginViewModel,
    email: String,
    password: String
) {
    val scope = rememberCoroutineScope()
    val isLoading = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SubmitFormButton(
            text = if (isLoading.value) "CONNEXION..." else "SE CONNECTER",
            onclick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    scope.launch {
                        isLoading.value = true
                        errorMessage.value = null

                        val result = viewModel.login(email, password)

                        result.onSuccess { token ->
                            errorMessage.value = null
                            isLoading.value = false
                            
                            scope.launch {
                                viewModel.saveToken(token)
                                redirectRoute("/")
                            }
                        }

                        result.onFailure { exception ->
                            errorMessage.value = exception.message ?: "Erreur de connexion"
                            isLoading.value = false
                        }
                    }
                } else {
                    errorMessage.value = "Veuillez remplir email et mot de passe"
                }
            },
            enabled = !isLoading.value
        )

        errorMessage.value?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}