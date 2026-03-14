package com.readymapeo.mobile.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.readymapeo.mobile.R

@Composable
fun Input(
    mutableValue: MutableState<String>,
    placeholder: String,
    textColor: Color = Color.Black,
    backgroundColor: Color = Color.Transparent,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(6.dp))
            .background(backgroundColor, RoundedCornerShape(6.dp))
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = mutableValue.value,
            onValueChange = { value ->
                mutableValue.value = value
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = textColor),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (mutableValue.value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = textColor.copy(alpha = 0.5f),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        innerTextField()
                    }

                    if (mutableValue.value.isNotEmpty()) {
                        IconButton(
                            onClick = { mutableValue.value = "" },
                            modifier = Modifier.padding(0.dp)
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.cross),
                                contentDescription = "clear_input",
                                tint = textColor,
                                modifier = Modifier.padding(0.dp)
                            )
                        }
                    }
                }
            }
        )
    }
}