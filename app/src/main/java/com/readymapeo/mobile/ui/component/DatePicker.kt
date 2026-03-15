package com.readymapeo.mobile.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.readymapeo.mobile.R
import com.readymapeo.mobile.utils.toFrenchDate

// composant en partie réalisé à l'aide de la documentation d'Android
// source : https://developer.android.com/develop/ui/compose/components/datepickers?hl=fr

@Composable
fun DatePicker(
    mutableSelectedDate: MutableState<Long?>,
    placeholder: String = "Choisir une date",
    backgroundColor: Color = Color.Transparent
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(6.dp))
            .background(backgroundColor, RoundedCornerShape(6.dp))
            .clickable { showDatePicker = true }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BasicTextField(
                value = mutableSelectedDate.value?.toFrenchDate() ?: "",
                onValueChange = {},
                readOnly = true,
                enabled = false,
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .padding(start = 12.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (mutableSelectedDate.value == null) {
                            Text(
                                text = placeholder,
                                color = Color.Black.copy(alpha = 0.5f),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        innerTextField()
                    }
                }
            )

            if (mutableSelectedDate.value != null) {
                IconButton(
                    onClick = { mutableSelectedDate.value = null },
                    modifier = Modifier.height(40.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.cross),
                        contentDescription = "clear_date_icon",
                    )
                }
            }

            IconButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.height(40.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.calendar),
                    contentDescription = "calendar_icon",
                )
            }
        }

        if (showDatePicker) {
            DatePickerModal(
                onDateSelected = { mutableSelectedDate.value = it },
                onDismiss = { showDatePicker = false }
            )
        }
    }
}

@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}