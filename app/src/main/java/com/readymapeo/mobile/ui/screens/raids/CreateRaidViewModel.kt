package com.readymapeo.mobile.ui.screens.raids

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.readymapeo.mobile.data.local.entity.Club
import com.readymapeo.mobile.data.local.entity.User
import com.readymapeo.mobile.data.local.entity.Raid
import com.readymapeo.mobile.data.repository.ClubRepository
import com.readymapeo.mobile.data.repository.RaidRepository
import com.readymapeo.mobile.data.repository.UserRepository
import com.readymapeo.mobile.manager.AuthManager
import com.readymapeo.mobile.manager.RolesManager
import com.readymapeo.mobile.network.ApiClient
import com.readymapeo.mobile.network.NetworkConnectivity
import com.readymapeo.mobile.utils.UserRole
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.delay
import org.json.JSONObject
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import android.util.Log
import com.readymapeo.mobile.data.api.RaidApiService

class CreateRaidViewModel(application: Application): AndroidViewModel(application) {
    val errorMessage = mutableStateOf("")
    val successMessage = mutableStateOf("")
    val showErrorToast = mutableStateOf(false)
    val showSuccessToast = mutableStateOf(false)

    val showClubDialog = mutableStateOf(false)
    val showManagerDialog = mutableStateOf(false)

    val searchClubMutable = mutableStateOf("")
    val searchManagerMutable = mutableStateOf("")

    val selectedClub = mutableStateOf<Club?>(null)
    val selectedUser = mutableStateOf<User?>(null)

    val eligibleClubs = mutableStateOf<List<Club>>(emptyList())
    val eligibleManagers = mutableStateOf<List<User>>(emptyList())


    val raidName = mutableStateOf("")
    val description = mutableStateOf("")
    val city = mutableStateOf("")
    val postalCode = mutableStateOf("")
    val street = mutableStateOf("")
    val contactEmail = mutableStateOf("")
    val website = mutableStateOf("")

    val raidDateStart = mutableStateOf<Long?>(null)
    val raidDateEnd = mutableStateOf<Long?>(null)
    val raidInsDateStart = mutableStateOf<Long?>(null)
    val raidInsDateEnd = mutableStateOf<Long?>(null)

    init {
        loadEligibleClubs()
    }

    fun loadEligibleClubs() {
        viewModelScope.launch {
            try {
                if (RolesManager.hasRole(UserRole.ADMIN)) {
                    ClubRepository.getAllClubs().collect { clubs ->
                        eligibleClubs.value = clubs
                    }
                }
                else if (RolesManager.hasRole(UserRole.RESPONSABLE_CLUB)) {
                    val managerId = AuthManager.getAuthenticatedUser()?.id ?: return@launch
                    ClubRepository.getClubsOfAManager(managerId).collect { clubs ->
                        eligibleClubs.value = clubs
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadEligibleManagers(clubId: Int) {
        viewModelScope.launch {
            try {
                UserRepository.getMembersByClubId(clubId).collect { members ->
                    eligibleManagers.value = members
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.value = "Erreur lors du chargement des responsables"
                showErrorToast.value = true
            }
        }
    }

     fun validateForm(): Boolean {
         return when {
             raidName.value.isBlank() -> {
                 errorMessage.value = "Le nom du raid est obligatoire"
                 showErrorToast.value = true
                 false
             }
             description.value.isBlank() -> {
                 errorMessage.value = "La description est obligatoire"
                 showErrorToast.value = true
                 false
             }
             raidDateStart.value == null || raidDateEnd.value == null -> {
                 errorMessage.value = "Les dates du raid sont obligatoires"
                 showErrorToast.value = true
                 false
             }
             raidInsDateStart.value == null || raidInsDateEnd.value == null -> {
                 errorMessage.value = "Les dates d'inscription sont obligatoires"
                 showErrorToast.value = true
                 false
             }
             raidInsDateEnd.value!! >= raidDateStart.value!! -> {
                 errorMessage.value = "La fin des inscriptions doit être avant le début du raid"
                 showErrorToast.value = true
                 false
             }
             (raidDateStart.value!! - raidInsDateEnd.value!!) < 86400000L -> {
                 errorMessage.value = "Les inscriptions doivent se terminer au moins un jour avant le début du raid"
                 showErrorToast.value = true
                 false
             }
             city.value.isBlank() -> {
                 errorMessage.value = "La ville est obligatoire"
                 showErrorToast.value = true
                 false
             }
             postalCode.value.isBlank() -> {
                 errorMessage.value = "Le code postal est obligatoire"
                 showErrorToast.value = true
                 false
             }
             selectedClub.value == null -> {
                 errorMessage.value = "Veuillez sélectionner un club organisateur"
                 showErrorToast.value = true
                 false
             }
             selectedUser.value == null -> {
                 errorMessage.value = "Veuillez sélectionner un responsable"
                 showErrorToast.value = true
                 false
             }
             contactEmail.value.isBlank() -> {
                 errorMessage.value = "L'email de contact est obligatoire"
                 showErrorToast.value = true
                 false
             }
             else -> {
                 errorMessage.value = ""
                 true
             }
         }
     }

    suspend fun createRaidPost(raidData: CreateRaidData): Result<Raid?> = withContext(Dispatchers.IO) {
         val body = JSONObject().apply {
             put("raid_name", raidData.raidName)
             put("clu_id", raidData.clubId)
             put("raid_date_start", formatDateTimeToISO(raidData.raidDateStart))
             put("raid_date_end", formatDateTimeToISO(raidData.raidDateEnd))
             put("ins_start_date", formatDateToISO(raidData.insDateStart))
             put("ins_end_date", formatDateToISO(raidData.insDateEnd))
             put("raid_description", raidData.raidDescription)
             put("raid_contact", raidData.raidContact)
             put("raid_street", raidData.raidStreet)
             put("raid_city", raidData.raidCity)
             put("raid_postal_code", raidData.raidPostalCode)
             //put("raid_number", raidData.raidNumber)
             put("raid_site_url", raidData.raidSiteUrl)
             put("raid_image", raidData.raidImage)
             put("adh_id", raidData.adhId)
             put("gestionnaire_raid_id", raidData.gestionnaireRaidId)
         }.toString()


         try {
             val response = ApiClient.post("/raids", body)

             val json = JSONObject(response)
             val status = json.optString("status", "unknown")

             if (status != "success") {
                 val error = json.optString("message", "Raid creation failed")

                 return@withContext Result.failure(Exception(error))
             }

             var raidJson = json.optJSONObject("data")

             if (raidJson == null) {
                 raidJson = json.optJSONObject("raid")
             }

             if (raidJson == null && json.has("raid_id")) {
                 raidJson = json
             }

             if (raidJson != null) {
                 val raid = RaidApiService.parseRaidJson(raidJson)
                 return@withContext Result.success(raid)
             }

             Result.failure(Exception("Le raid à été crée et sera bientot affiché dans l'interface il est deja disponible sur la version web"))
         } catch (e: Exception) {
             Result.failure(e)
         }
     }

     fun createRaid(raidData: CreateRaidData, onSuccess: () -> Unit) {
         viewModelScope.launch {
             try {
                 if (NetworkConnectivity.isOnline.value) {
                     val result = createRaidPost(raidData)
                     if (result.isSuccess) {
                         val raid = result.getOrNull()
                         if (raid != null) {
                             withContext(Dispatchers.IO) {
                                 RaidRepository.insertRaid(raid)
                             }
                         }
                         onSuccess()
                     } else {
                         errorMessage.value = result.exceptionOrNull()?.message ?: "Erreur lors de la création du raid"
                         showErrorToast.value = true
                     }
                 } else {
                     val tempId = -(System.currentTimeMillis() / 1000).toInt()

                     val raid = Raid(
                         raidId = tempId,
                         raidName = raidData.raidName,
                         raidDescription = raidData.raidDescription,
                         adhId = raidData.adhId,
                         cluId = raidData.clubId,
                         insId = 0,
                         raidDateStart = formatDateTimeToISO(raidData.raidDateStart),
                         raidDateEnd = formatDateTimeToISO(raidData.raidDateEnd),
                         raidContact = raidData.raidContact,
                         raidSiteUrl = raidData.raidSiteUrl.ifBlank { null },
                         raidImage = raidData.raidImage.ifBlank { null },
                         raidStreet = raidData.raidStreet,
                         raidCity = raidData.raidCity,
                         raidPostalCode = raidData.raidPostalCode,
                         raidNumber = raidData.raidNumber,
                         createdAt = System.currentTimeMillis().toString(),
                         updatedAt = System.currentTimeMillis().toString(),
                         insStartDate = formatDateToISO(raidData.insDateStart),
                         insEndDate = formatDateToISO(raidData.insDateEnd),
                         isSynced = false, // Mark as not synced
                         lastSyncAt = 0
                     )

                     withContext(Dispatchers.IO) {
                         RaidRepository.insertRaid(raid)
                     }

                     successMessage.value = "Raid créé hors ligne\n\nVotre raid a été créé avec succès et sera synchronisé automatiquement lorsque la connexion Internet sera rétablie."
                     showSuccessToast.value = true

                     delay(2000)
                     onSuccess()
                 }
             } catch (e: Exception) {
                 errorMessage.value = e.message ?: "Erreur lors de la création du raid"
                 showErrorToast.value = true
             }
         }
     }

    private fun formatDateTimeToISO(timestamp: Long): String {
        val instant = Instant.ofEpochMilli(timestamp)
        val formatter = DateTimeFormatter.ISO_INSTANT
        return formatter.format(instant)
    }

    private fun formatDateToISO(timestamp: Long): String {
        val localDate = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timestamp),
            ZoneId.systemDefault()
        ).toLocalDate()
        return localDate.toString()
    }
}