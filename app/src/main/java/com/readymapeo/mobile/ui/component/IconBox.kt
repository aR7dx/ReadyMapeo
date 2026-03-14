package com.readymapeo.mobile.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconBox(
    icon: ImageVector,
    iconColor: Color,
    iconPadding: Dp = 8.dp,
    backgroundColor: Color,
    borderColor: Color = Color.Transparent,
    radius: Dp,
) {
    Box(modifier = Modifier
        .border(1.dp, borderColor, RoundedCornerShape(radius))
        .background(
            color = backgroundColor,
            RoundedCornerShape(radius)
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "icon",
            tint = iconColor,
            modifier = Modifier.padding(iconPadding)
        )
    }
}