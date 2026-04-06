package com.readymapeo.mobile.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun DateRangeWithTime(
    label: String? = null,
    mutableValue1: MutableState<Long?>,
    mutableValue2: MutableState<Long?>,
    placeholder1: String = "Date et heure de début",
    placeholder2: String = "Date et heure de fin",
    includeTime: Boolean = true,
) {
    var isDateCorrect by remember { mutableStateOf(true) }

    // États pour les heures et minutes
    var startHour by remember { mutableStateOf("08") }
    var startMinute by remember { mutableStateOf("00") }
    var endHour by remember { mutableStateOf("18") }
    var endMinute by remember { mutableStateOf("00") }

    Column {
        if (!label.isNullOrBlank()) {
            Text(label, style = MaterialTheme.typography.titleSmall)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Date de début avec heure
        Column {
            Text("Date de début", style = MaterialTheme.typography.bodySmall)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                DatePickerField(
                    modifier = Modifier.weight(1f),
                    mutableSelectedDate = mutableValue1,
                    placeholder = placeholder1,
                    isValid = isDateCorrect
                )

                if (includeTime) {
                    // Heure
                    TextField(
                        value = startHour,
                        onValueChange = { newValue ->
                            val filtered = newValue.filter { it.isDigit() }
                            startHour = if (filtered.length > 2) filtered.take(2) else filtered.padStart(2, '0')
                            updateDateTime(mutableValue1, startHour, startMinute)
                        },
                        modifier = Modifier.width(60.dp),
                        label = { Text("HH") },
                        singleLine = true
                    )

                    Text(":", style = MaterialTheme.typography.bodyLarge)

                    // Minutes
                    TextField(
                        value = startMinute,
                        onValueChange = { newValue ->
                            val filtered = newValue.filter { it.isDigit() }
                            startMinute = if (filtered.length > 2) filtered.take(2) else filtered.padStart(2, '0')
                            updateDateTime(mutableValue1, startHour, startMinute)
                        },
                        modifier = Modifier.width(60.dp),
                        label = { Text("MM") },
                        singleLine = true
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Date de fin avec heure
        Column {
            Text("Date de fin", style = MaterialTheme.typography.bodySmall)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                DatePickerField(
                    modifier = Modifier.weight(1f),
                    mutableSelectedDate = mutableValue2,
                    placeholder = placeholder2,
                    isValid = isDateCorrect
                )

                if (includeTime) {
                    // Heure
                    TextField(
                        value = endHour,
                        onValueChange = { newValue ->
                            val filtered = newValue.filter { it.isDigit() }
                            endHour = if (filtered.length > 2) filtered.take(2) else filtered.padStart(2, '0')
                            updateDateTime(mutableValue2, endHour, endMinute)
                        },
                        modifier = Modifier.width(60.dp),
                        label = { Text("HH") },
                        singleLine = true
                    )

                    Text(":", style = MaterialTheme.typography.bodyLarge)

                    // Minutes
                    TextField(
                        value = endMinute,
                        onValueChange = { newValue ->
                            val filtered = newValue.filter { it.isDigit() }
                            endMinute = if (filtered.length > 2) filtered.take(2) else filtered.padStart(2, '0')
                            updateDateTime(mutableValue2, endHour, endMinute)
                        },
                        modifier = Modifier.width(60.dp),
                        label = { Text("MM") },
                        singleLine = true
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        isDateCorrect = isDateOk(
            mutableValue1.value ?: 0,
            mutableValue2.value ?: 0
        )
    }
}

@Composable
fun DatePickerField(
    modifier: Modifier = Modifier,
    mutableSelectedDate: MutableState<Long?>,
    placeholder: String = "Choisir une date",
    isValid: Boolean = true
) {
    DatePicker(
        modifier = modifier,
        mutableSelectedDate = mutableSelectedDate,
        placeholder = placeholder,
        isValid = isValid
    )
}


