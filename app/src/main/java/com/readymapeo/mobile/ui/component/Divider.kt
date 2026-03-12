package com.readymapeo.mobile.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Divider(modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(4.dp))
    HorizontalDivider(
        modifier = modifier,
        color = Color(0xFFE6E6ED)
    )
    Spacer(modifier = Modifier.height(4.dp))
}