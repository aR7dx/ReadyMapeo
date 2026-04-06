package com.readymapeo.mobile.ui.component.placeholder

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.readymapeo.mobile.R

@Preview(showBackground = true)
@Composable
fun RaceImageTemplate(
    height: Dp = 250.dp
) {
    CardImageTemplate(
        height = height,
        backgroundColor = Color(0xFFE7ECFF),
        imageVector = ImageVector.vectorResource(R.drawable.trophy),
        iconColor =  Color(0xFFA5B4FC)
    )
}