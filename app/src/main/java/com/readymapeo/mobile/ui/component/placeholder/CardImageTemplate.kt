package com.readymapeo.mobile.ui.component.placeholder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.readymapeo.mobile.R

@Preview(showBackground = true)
@Composable
fun CardImageTemplate(
    backgroundColor: Color = Color(0xFFE7ECFF),
    imageVector: ImageVector = ImageVector.vectorResource(R.drawable.trophy),
    iconColor: Color = Color(0xFFA5B4FC),
    height: Dp = 250.dp,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .height(height)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "alternative_icon",
            tint = iconColor,
            modifier = Modifier.size(100.dp)
        )
    }
}