package com.readymapeo.mobile.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.readymapeo.mobile.R

val Figtree = FontFamily(
    Font(R.font.figtree_regular, FontWeight.Normal),
    Font(R.font.figtree_medium, FontWeight.Medium),
    Font(R.font.figtree_semibold, FontWeight.SemiBold),
    Font(R.font.figtree_bold, FontWeight.Bold),
    Font(R.font.figtree_italic, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle( // H1
        fontFamily = Figtree,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        color = Color(0xFF111827)
    ),
    displayMedium = TextStyle( // H2
        fontFamily = Figtree,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = Color(0xFF111827)
    ),
    displaySmall = TextStyle( // H3
        fontFamily = Figtree,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        color = Color(0xFF111827)
    ),
    headlineLarge = TextStyle( // H4
        fontFamily = Figtree,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = Color(0xFF111827)
    ),
    headlineMedium = TextStyle( // H5
        fontFamily = Figtree,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        color = Color(0xFF111827)
    ),
    headlineSmall = TextStyle( // H6
        fontFamily = Figtree,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        color = Color(0xFF111827)
    ),
    bodyLarge = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color(0xFF374151)
    ),
    bodyMedium = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Color(0xFF4B5563)
    ),
    bodySmall = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = Color(0xFF6B7280)
    ),
    labelLarge = TextStyle(
        fontFamily = Figtree,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = Color(0xFF374151)
    )
)
