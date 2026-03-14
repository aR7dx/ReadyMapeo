package com.readymapeo.mobile.ui.component.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.readymapeo.mobile.R
import com.readymapeo.mobile.ui.component.DatePicker
import com.readymapeo.mobile.ui.component.Divider
import com.readymapeo.mobile.ui.component.Input
import com.readymapeo.mobile.ui.component.Select
import com.readymapeo.mobile.ui.theme.BoldTypography

data class RaidsFilterData(
    val locationScope: String,
    val locationInputValue: String = "",
    val date: Long? = null,
    val type: String = "",
    val category: String = ""
)

@Composable
fun RaidsFilterForm(
    submitButtonColor: Color = Color(0xFF1F2937),
    submitIconColor: Color = Color.White,
    submitTextColor: Color = Color.White,
    backgroundColor: Color = Color.White,
    dividerColor: Color = Color(0xFFE6E6ED),
    onFilterChange: (RaidsFilterData) -> Unit = {}
) {
    val locationScopes = listOf("Ville", "Département", "Région")
    val raidType = listOf("Tous", "Loisir", "Compétition")
    val raidCategory = listOf("Tous", "Benjamins", "Minimes", "Cadets", "Juniors", "Espoirs", "Séniors", "Vétérans")

    // mutable
    val selectedLocationScope = remember { mutableStateOf(locationScopes[0]) }
    val locationInputValue = remember { mutableStateOf("") }
    val selectedDate = remember { mutableStateOf<Long?>(null) }
    val selectedType = remember { mutableStateOf(raidType[0]) }
    val selectedCategory = remember { mutableStateOf(raidCategory[0]) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            LocationRow(
                data = locationScopes,
                mutable = selectedLocationScope,
                mutable2 = locationInputValue
            )

            DateRow(mutableSelectedDate = selectedDate)

            TypeAndCategoryRow(
                selectedType = selectedType,
                typeList = raidType,
                selectedCategory = selectedCategory,
                categoryList = raidCategory
            )

            Divider(dividerColor = dividerColor)

            // submit form button
            Button(
                onClick = {
                    onFilterChange(RaidsFilterData(
                        locationScope = selectedLocationScope.value,
                        locationInputValue = locationInputValue.value,
                        date = selectedDate.value,
                        type = selectedType.value,
                        category = selectedCategory.value
                    ))
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = submitButtonColor),
                shape = RoundedCornerShape(6.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.search),
                        contentDescription = "search_icon",
                        tint = submitIconColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Rechercher",
                        color = submitTextColor,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}

@Composable
fun LocationRow(data: List<String>, mutable: MutableState<String>, mutable2: MutableState<String>) {
    Column {
        Text(
            text = "OÙ ?",
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth(),
            color = Color(0xFF9EA5B1),
            style = BoldTypography.bodyMediumBold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Select(
                modifier = Modifier.fillMaxWidth(0.5f),
                data = data,
                onSelectionChange = { selectedValue ->
                    mutable.value = selectedValue
                }
            )

            Input(
                mutableValue = mutable2,
                placeholder = "Ville, Région..."
            )
        }
    }
}

@Composable
fun DateRow(mutableSelectedDate: MutableState<Long?>) {
    Column {
        Text(
            text = "QUAND ?",
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth(),
            color = Color(0xFF9EA5B1),
            style = BoldTypography.bodyMediumBold
        )

        DatePicker(mutableSelectedDate = mutableSelectedDate, placeholder = "Toutes les dates")
    }
}

@Composable
fun TypeAndCategoryRow(selectedType: MutableState<String>, typeList: List<String>, selectedCategory: MutableState<String>, categoryList: List<String>) {
    Column {
        Text(
            text = "TYPE ET ÂGE",
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth(),
            color = Color(0xFF9EA5B1),
            style = BoldTypography.bodyMediumBold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Select(
                modifier = Modifier.fillMaxWidth(0.5f),
                data = typeList,
                onSelectionChange = { chooseType ->
                    selectedType.value = chooseType
                }
            )

            Select(
                data = categoryList,
                onSelectionChange = { chooseCategory ->
                    selectedCategory.value = chooseCategory
                }
            )
        }
    }
}