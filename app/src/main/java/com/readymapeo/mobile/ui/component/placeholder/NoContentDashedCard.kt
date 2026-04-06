package com.readymapeo.mobile.ui.component.placeholder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.style.TextAlign
import com.readymapeo.mobile.R
import com.readymapeo.mobile.ui.component.IconBox

@Preview(showBackground = true)
@Composable
fun NotContentDashedCard(
    title: String = "PLACEHOLDER",
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium,
    titleColor: Color = Color(0xFF1e3A8A),
    subText: String = "SUBTEXT PLACEHOLDER",
    subTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    subTextColor: Color = Color(0xFFA9BBF0),
    icon: ImageVector = ImageVector.vectorResource(R.drawable.trophy),
    iconColor: Color = Color(0xFFBFDBFE),
    iconBoxColor: Color = Color(0xFFEFF6FF),
    stokeColor: Color = Color(0xFFDBEAFE),
    radius: Dp = 99.dp,
    cardBackgroundColor: Color = Color.White,
    composable: @Composable () -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .drawWithContent {
                drawContent()
                
                val stroke = 2.dp.toPx()
                val radius = 12.dp.toPx()
                
                drawRoundRect(
                    color = stokeColor,
                    topLeft = Offset(stroke / 2, stroke / 2),
                    size = Size(
                        width = size.width - stroke,
                        height = size.height - stroke
                    ),
                    cornerRadius = CornerRadius(radius),
                    style = Stroke(
                        width = stroke,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                )
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            IconBox(
                icon = icon,
                iconColor = iconColor,
                backgroundColor = iconBoxColor,
                radius = radius
            )

            Text(
                text = title,
                style = titleStyle,
                color = titleColor
            )

            Text(
                text = subText,
                style = subTextStyle,
                textAlign = TextAlign.Center,
                color = subTextColor
            )

            composable()
        }
    }
}