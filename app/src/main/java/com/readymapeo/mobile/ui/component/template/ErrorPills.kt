package com.readymapeo.mobile.ui.component.template

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.readymapeo.mobile.ui.component.Pills

@Preview(showBackground = true)
@Composable
fun ErrorPills(
    text: String = "Pills component",
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
) {
    Pills(
        text = text,
        textStyle = textStyle,
        textColor = Color(0xFF991B1B),
        borderColor = Color(0xFFFECACA),
        backgroundColor = Color(0xFFFEE2E2)
    )
}