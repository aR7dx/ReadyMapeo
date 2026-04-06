package com.readymapeo.mobile.ui.screens.raids

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.readymapeo.mobile.R
import com.readymapeo.mobile.routes.redirectRoute
import com.readymapeo.mobile.ui.component.CTAButton
import com.readymapeo.mobile.ui.component.DateRange
import com.readymapeo.mobile.ui.component.ErrorToast
import com.readymapeo.mobile.ui.component.SuccessToast
import com.readymapeo.mobile.ui.component.Input
import com.readymapeo.mobile.ui.component.TextArea
import com.readymapeo.mobile.ui.component.UploadBox
import com.readymapeo.mobile.ui.component.forms.CardSection
import com.readymapeo.mobile.ui.component.ListPicker
import com.readymapeo.mobile.ui.component.forms.ClubListItem
import com.readymapeo.mobile.ui.component.forms.UserListItem
import com.readymapeo.mobile.ui.component.navigation.BackNavbar
import com.readymapeo.mobile.data.local.entity.Club
import com.readymapeo.mobile.data.local.entity.User

data class CreateRaidData(
    val raidName: String,
    val clubId: Int,
    val raidDateStart: Long,
    val raidDateEnd: Long,
    val insDateStart: Long,
    val insDateEnd: Long,
    val raidDescription: String,
    val raidContact: String,
    val raidStreet: String,
    val raidCity: String,
    val raidPostalCode: String,
    val raidNumber: Int,
    val raidSiteUrl: String,
    val raidImage: String,
    val adhId: Int,
    val gestionnaireRaidId: Int,
)

@Composable
fun CreateRaidScreen(
    viewModel: CreateRaidViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        BackNavbar { redirectRoute("/raids") }

        CardSection(title = "Informations générales") {

            Input(
                mutableValue = viewModel.raidName,
                label = "Nom du raid*",
            )

            Text(
                text = "${viewModel.raidName.value.length}/100 caractères",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(Modifier.height(12.dp))

            TextArea(
                mutableValue = viewModel.description,
                label = "Description*"
            )

            Spacer(Modifier.height(16.dp))

            DateRange(
                label = "Date du raid*",
                mutableValue1 = viewModel.raidDateStart,
                mutableValue2 = viewModel.raidDateEnd,
                includeTime = true
            )
            DateRange(
                label = "Période d'inscription*",
                mutableValue1 = viewModel.raidInsDateStart,
                mutableValue2 = viewModel.raidInsDateEnd,
                includeTime = false
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Les inscriptions doivent se terminer au plus tard la veille du début du raid",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(Modifier.height(16.dp))

            Text("Lieu", style = MaterialTheme.typography.titleMedium)

            Input(
                mutableValue = viewModel.city,
                label = "Ville*",
            )
            Input(
                mutableValue = viewModel.postalCode,
                label = "Code postal*",
            )

            Spacer(Modifier.height(8.dp))

            Input(
                mutableValue = viewModel.street,
                label = "Rue (optionnel)",
                placeholder = "Adresse du point de rendez-vous"
            )
        }

        Spacer(Modifier.height(16.dp))

        CardSection(title = "Organisation") {

            Text("Club organisateur*")
            if (viewModel.selectedClub.value != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFE5E7EB), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = viewModel.selectedClub.value?.clubName ?: "Pas de club sélectionné",
                    )
                }
            } else {
                CTAButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Sélectionner un club",
                    textColor = Color.White,
                    backgroundColor = Color(0xFF4F46E5),
                    icon = ImageVector.vectorResource(R.drawable.clubs),
                    iconOnLeft = true,
                    onclick = { viewModel.showClubDialog.value = true },
                )
            }

            Spacer(Modifier.height(12.dp))

            Text("Responsable du raid*")

            if (viewModel.selectedUser.value != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFE5E7EB), RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Text(
                        text = viewModel.selectedUser.value?.name ?: "Pas de responsable sélectionné",
                    )
                }
            } else {
                CTAButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Sélectionner un responsable",
                    textColor = Color.White,
                    backgroundColor = Color(0xFF4F46E5),
                    icon = ImageVector.vectorResource(R.drawable.people),
                    iconOnLeft = true,
                    enabled = viewModel.selectedClub.value != null,
                    onclick = { viewModel.showManagerDialog.value = true },
                )
            }

            Text(
                "Membres adhérents du club ${viewModel.selectedClub.value?.clubName ?: "<Introuvable>"}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(Modifier.height(12.dp))

            Input(
                mutableValue = viewModel.contactEmail,
                label = "Email de contact",
                placeholder = "email@example.com"
            )
        }

        Spacer(Modifier.height(16.dp))

        // SECTION 3
        CardSection(title = "Informations complémentaires") {
            Input(
                mutableValue = viewModel.website,
                label = "Site Web (optionnel)",
                placeholder = "https://example.com"
            )

            Spacer(Modifier.height(16.dp))

            Text("Image du raid")
            Text(
                "Image principale qui sera affichée sur la page du raid",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(Modifier.height(8.dp))

            UploadBox()
        }

        Spacer(Modifier.height(24.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF10B981),
                disabledContainerColor = Color(0xFFC8E6C9)
            ),
            enabled = viewModel.selectedClub.value != null && viewModel.selectedUser.value != null,
            onClick = {
                if (viewModel.validateForm()) {
                    viewModel.createRaid(
                        CreateRaidData(
                            raidName = viewModel.raidName.value,
                            clubId = viewModel.selectedClub.value!!.clubId,
                            raidDateStart = viewModel.raidDateStart.value!!,
                            raidDateEnd = viewModel.raidDateEnd.value!!,
                            insDateStart = viewModel.raidInsDateStart.value!!,
                            insDateEnd = viewModel.raidInsDateEnd.value!!,
                            raidDescription = viewModel.description.value,
                            raidContact = viewModel.contactEmail.value,
                            raidStreet = viewModel.street.value,
                            raidCity = viewModel.city.value,
                            raidPostalCode = viewModel.postalCode.value,
                            raidNumber = 99,
                            raidSiteUrl = viewModel.website.value,
                            raidImage = "", // TODO: handle image upload,
                            adhId = viewModel.selectedUser.value!!.id,
                            gestionnaireRaidId = viewModel.selectedUser.value!!.id
                        ),
                        onSuccess = {
                            redirectRoute("/raids")
                        }
                    )
                }
            },
        ) {
            Text("Créer le raid")
        }

        Spacer(Modifier.height(32.dp))
    }

    ErrorToast(
        message = viewModel.errorMessage.value,
        visible = viewModel.showErrorToast.value,
        onDismiss = { viewModel.showErrorToast.value = false }
    )

    SuccessToast(
        message = viewModel.successMessage.value,
        visible = viewModel.showSuccessToast.value,
        duration = 5000,
        onDismiss = { viewModel.showSuccessToast.value = false }
    )

    if (viewModel.showClubDialog.value) {
        ListPicker(
            title = "Sélectionner un club",
            inputPlaceholder = "Rechercher un club...",
            searchMutable = viewModel.searchClubMutable,
            items =
                viewModel.eligibleClubs.value.filter {
                    it.clubName.contains(viewModel.searchClubMutable.value, ignoreCase = true) ||
                    it.clubCity.contains(viewModel.searchClubMutable.value, ignoreCase = true) ||
                    it.clubStreet.contains(viewModel.searchClubMutable.value, ignoreCase = true)
                },
            listItem = { club, onClick ->
                ClubListItem(club) { onClick() }
            },
            onDismiss = { viewModel.showClubDialog.value = false },
            onItemSelected = { club: Club ->
                viewModel.selectedClub.value = club
                viewModel.loadEligibleManagers(club.clubId)
            }
        )
    }

    if (viewModel.showManagerDialog.value) {
        ListPicker(
            title = "Sélectionner un responsable",
            inputPlaceholder = "Rechercher un utilisateur...",
            searchMutable = viewModel.searchManagerMutable,
            items =
                viewModel.eligibleManagers.value,
            listItem = { user, onClick ->
                UserListItem(user) { onClick() }
            },
            onDismiss = { viewModel.showManagerDialog.value = false },
            onItemSelected = { user: User ->
                viewModel.selectedUser.value = user
            }
        )
    }
}