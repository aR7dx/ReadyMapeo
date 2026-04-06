package com.readymapeo.mobile.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.readymapeo.mobile.ui.theme.SemiBoldTypography

@Composable
fun TextArea(
    mutableValue: MutableState<String>,
    label: String? = null,
    textColor: Color = Color.Black,
    onValueChange: () -> Unit = {},
) {
    Column {
        if (!label.isNullOrBlank()) {
            Text(
                text = label,
                style = SemiBoldTypography.bodyMedium,
            )
        }

        Spacer(Modifier.height(4.dp))

        OutlinedTextField(
            value = mutableValue.value,
            onValueChange = { value ->
                mutableValue.value = value
                onValueChange()
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = textColor),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(6.dp))
        )
    }
}