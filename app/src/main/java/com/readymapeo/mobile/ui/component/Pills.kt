package com.readymapeo.mobile.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview(showBackground = true)
@Composable
fun Pills (
    text: String = "Pills component",
    textColor: Color = Color(0xFF000000),
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    icon: ImageVector? = null,
    iconColor: Color? = null,
    backgroundColor: Color = Color(0xFFFFFF1A)
) {
    Box(
        modifier = Modifier
            .padding(2.dp)
            .background(
                color = backgroundColor,
                RoundedCornerShape(25.dp)
            )
            .clip(RoundedCornerShape(25.dp))

    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "icon",
                    tint = iconColor ?: Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            Text (
                text = text,
                style = textStyle,
                color = textColor,
                lineHeight = 16.sp
            )
        }
    }
}