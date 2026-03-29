package com.readymapeo.mobile.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.readymapeo.mobile.R
import com.readymapeo.mobile.ui.theme.SemiBoldTypography
import com.readymapeo.mobile.utils.InputType

@Composable
fun Input(
    type: InputType = InputType.TEXT,
    mutableValue: MutableState<String>,
    label: String? = null,
    labelColor: Color = Color.DarkGray,
    placeholder: String = "",
    textColor: Color = Color.Black,
    backgroundColor: Color = Color.Transparent,
    onValueChange: () -> Unit = {},
) {
    val keyboardType = when (type) {
        InputType.TEXT -> KeyboardType.Text
        InputType.TEL -> KeyboardType.Phone
        InputType.EMAIL -> KeyboardType.Email
        InputType.PASSWORD -> KeyboardType.Password
    }

    // State pour gérer la visibilité du mot de passe
    val isPasswordVisible = remember { mutableStateOf(false) }

    val visualTransformation = when (type) {
        InputType.PASSWORD -> {
            if (isPasswordVisible.value) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        }
        else -> VisualTransformation.None
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!label.isNullOrBlank()) {
            Text(
                text = label,
                color = labelColor,
                style = SemiBoldTypography.bodyMedium,
                modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
            )
        }

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
                    onValueChange()
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                visualTransformation = visualTransformation,
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

                        if (type == InputType.PASSWORD) {
                            IconButton(
                                onClick = {
                                    isPasswordVisible.value = !isPasswordVisible.value
                                },
                                modifier = Modifier.padding(0.dp)
                            ) {
                                Icon(
                                    imageVector =
                                        if (isPasswordVisible.value) {
                                            ImageVector.vectorResource(R.drawable.visibility)
                                        } else {
                                            ImageVector.vectorResource(R.drawable.visibility_off)
                                        },
                                    contentDescription = "show_hider_password_icon",
                                    tint = textColor,
                                    modifier = Modifier.padding(0.dp)
                                )
                            }
                        }

                        if (mutableValue.value.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    mutableValue.value = ""
                                    onValueChange()
                                },
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
}