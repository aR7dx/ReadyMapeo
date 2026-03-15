package com.readymapeo.mobile.ui.component.form

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.readymapeo.mobile.ui.screens.raids.RaidsViewModel

@Composable
fun CollapsibleRaidsFilterForm(viewModel: RaidsViewModel, isOpen: Boolean = false) {
    val isFilterExpanded = remember { mutableStateOf(isOpen) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isFilterExpanded.value = !isFilterExpanded.value
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Filtrer les raids",
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(onClick = { isFilterExpanded.value = !isFilterExpanded.value }) {
                Icon(
                    imageVector = if (isFilterExpanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "arrow_down_icon"
                )
            }
        }

        AnimatedVisibility(
            visible = isFilterExpanded.value,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            RaidsFilterForm(
                viewModel = viewModel,
                onFilterChange = { filterData ->
                    viewModel.filterRaids(
                        filterData.locationScope,
                        filterData.locationInputValue,
                        filterData.date
                    )
                }
            )
        }
    }
}