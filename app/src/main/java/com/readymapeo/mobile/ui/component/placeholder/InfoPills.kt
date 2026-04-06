package com.readymapeo.mobile.ui.component.placeholder

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.readymapeo.mobile.ui.component.Pills

@Preview(showBackground = true)
@Composable
fun InfoPills(
    text: String = "Pills component",
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
) {
    Pills(
        text = text,
        textStyle = textStyle,
        textColor = Color(0xFF3730A3),
        borderColor = Color(0xFFC7D2FE),
        backgroundColor = Color(0xFFE0E7FF)
    )
}
