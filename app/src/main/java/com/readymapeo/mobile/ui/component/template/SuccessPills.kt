package com.readymapeo.mobile.ui.component.template

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.readymapeo.mobile.ui.component.Pills

@Preview(showBackground = true)
@Composable
fun SuccessPills(
    text: String = "Pills component",
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
) {
    Pills(
        text = text,
        textStyle = textStyle,
        textColor = Color(0xFF166534),
        borderColor = Color(0xFFBBF7D0),
        backgroundColor = Color(0xFFDCFCE7)
    )
}