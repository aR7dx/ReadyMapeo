package com.readymapeo.mobile.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
fun DateRange(
    label: String? = null,
    mutableValue1 : MutableState<Long?>,
    mutableValue2 : MutableState<Long?>,
    placeholder1: String = "jj / mm / aaaa",
    placeholder2: String = "jj / mm / aaaa",
    includeTime: Boolean = false,
) {

    var isDateCorrect by remember { mutableStateOf(true) }
    var startHour by remember { mutableStateOf("08") }
    var startMinute by remember { mutableStateOf("00") }
    var endHour by remember { mutableStateOf("18") }
    var endMinute by remember { mutableStateOf("00") }

    Column {
        if (!label.isNullOrBlank()) {
            Text(label)
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Date de début
        if (includeTime) {
            Text("Date et heure de début", style = MaterialTheme.typography.bodySmall)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DatePicker(
                modifier = Modifier.fillMaxWidth(if (includeTime) 0.5f else 0.5f),
                mutableSelectedDate = mutableValue1,
                placeholder = placeholder1,
                onValueChange = {
                    isDateCorrect = isDateOk(
                        mutableValue1.value ?: 0,
                        mutableValue2.value ?: 0
                    )
                },
                isValid = isDateCorrect
            )

            if (includeTime) {
                TextField(
                    value = startHour,
                    onValueChange = { newValue ->
                        val filtered = newValue.filter { it.isDigit() }
                        startHour = if (filtered.length > 2) filtered.take(2) else filtered.padStart(2, '0')
                        updateDateTime(mutableValue1, startHour, startMinute)
                    },
                    modifier = Modifier.fillMaxWidth(0.35f),
                    label = { Text("HH") },
                    singleLine = true
                )

                Text(":")

                TextField(
                    value = startMinute,
                    onValueChange = { newValue ->
                        val filtered = newValue.filter { it.isDigit() }
                        startMinute = if (filtered.length > 2) filtered.take(2) else filtered.padStart(2, '0')
                        updateDateTime(mutableValue1, startHour, startMinute)
                    },
                    modifier = Modifier.fillMaxWidth(1f),
                    label = { Text("MM") },
                    singleLine = true
                )
            }
        }

        if (includeTime) {
            Spacer(modifier = Modifier.height(12.dp))
            Text("Date et heure de fin", style = MaterialTheme.typography.bodySmall)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DatePicker(
                modifier = Modifier.fillMaxWidth(if (includeTime) 0.5f else 1f),
                mutableSelectedDate = mutableValue2,
                placeholder = placeholder2,
                onValueChange = {
                    isDateCorrect = isDateOk(
                        mutableValue1.value ?: 0,
                        mutableValue2.value ?: 0
                    )
                },
                isValid = isDateCorrect
            )

            if (includeTime) {
                TextField(
                    value = endHour,
                    onValueChange = { newValue ->
                        val filtered = newValue.filter { it.isDigit() }
                        endHour = if (filtered.length > 2) filtered.take(2) else filtered.padStart(2, '0')
                        updateDateTime(mutableValue2, endHour, endMinute)
                    },
                    modifier = Modifier.fillMaxWidth(0.35f),
                    label = { Text("HH") },
                    singleLine = true
                )

                Text(":")

                TextField(
                    value = endMinute,
                    onValueChange = { newValue ->
                        val filtered = newValue.filter { it.isDigit() }
                        endMinute = if (filtered.length > 2) filtered.take(2) else filtered.padStart(2, '0')
                        updateDateTime(mutableValue2, endHour, endMinute)
                    },
                    modifier = Modifier.fillMaxWidth(1f),
                    label = { Text("MM") },
                    singleLine = true
                )
            }
        }
    }
}

fun isDateOk(dateStart: Long, dateEnd: Long): Boolean {
    return if (dateStart >= dateEnd) false
    else true
}

fun updateDateTime(mutableValue: MutableState<Long?>, hour: String, minute: String) {
    val date = mutableValue.value?.let {
        LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
    }

    if (date != null) {
        try {
            val newHour = hour.toIntOrNull() ?: 0
            val newMinute = minute.toIntOrNull() ?: 0

            if (newHour in 0..23 && newMinute in 0..59) {
                val newDateTime = date.withHour(newHour).withMinute(newMinute).withSecond(0)
                mutableValue.value = newDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            }
        } catch (_: Exception) {}
    }
}

