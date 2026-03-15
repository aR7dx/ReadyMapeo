package com.readymapeo.mobile.ui.component.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.readymapeo.mobile.ui.screens.raids.RaidsViewModel
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
    viewModel: RaidsViewModel,
    submitButtonColor: Color = Color(0xFF1F2937),
    submitIconColor: Color = Color.White,
    submitTextColor: Color = Color.White,
    backgroundColor: Color = Color.White,
    dividerColor: Color = Color(0xFFE6E6ED),
    onFilterChange: (RaidsFilterData) -> Unit = {}
) {


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
                data = viewModel.locationScopes,
                mutable = viewModel.selectedLocationScope,
                mutable2 = viewModel.locationInputValue
            )

            DateRow(mutableSelectedDate = viewModel.selectedDate)

            TypeAndCategoryRow(
                selectedType = viewModel.selectedType,
                typeList = viewModel.raidType,
                selectedCategory = viewModel.selectedCategory,
                categoryList = viewModel.raidCategory
            )

            Divider(dividerColor = dividerColor)

            SubmitFormButton(
                text = "Rechercher...",
                textColor = submitTextColor,
                backgroundColor = submitButtonColor,
                icon = ImageVector.vectorResource(R.drawable.search),
                iconColor = submitIconColor,
                iconOnLeft = true,
                onclick = {
                    onFilterChange(RaidsFilterData(
                        locationScope = viewModel.selectedLocationScope.value,
                        locationInputValue = viewModel.locationInputValue.value,
                        date = viewModel.selectedDate.value,
                        type = viewModel.selectedType.value,
                        category = viewModel.selectedCategory.value
                    ))
                }
            )
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
            style = BoldTypography.bodyMedium
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
            style = BoldTypography.bodyMedium
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
            style = BoldTypography.bodyMedium
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