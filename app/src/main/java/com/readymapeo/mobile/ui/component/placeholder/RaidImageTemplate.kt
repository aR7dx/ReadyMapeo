package com.readymapeo.mobile.ui.component.placeholder

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.readymapeo.mobile.R

@Composable
fun RaidImageTemplate() {
    CardImageTemplate(
        backgroundColor = Color(0xFFE7ECFF),
        imageVector = ImageVector.vectorResource(R.drawable.trophy),
        iconColor =  Color(0xFFA5B4FC)
    )
}