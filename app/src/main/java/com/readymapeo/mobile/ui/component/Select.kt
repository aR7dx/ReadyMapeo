package com.readymapeo.mobile.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// composant en partie réalisé à l'aide de la documentation d'android
// source : https://developer.android.com/develop/ui/compose/components/menu?hl=fr

@Composable
fun Select(
    modifier: Modifier = Modifier,
    data: List<String>,
    onSelectionChange: (String) -> Unit = {},
    textColor: Color = Color.Black,
    iconColor: Color = Color.Black,
    border: BorderStroke =  BorderStroke(1.dp, Color.LightGray),
    backgroundColor: Color = Color.Transparent
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf<String>(data[0]) }

    Box(modifier = modifier) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { expanded = !expanded },
            shape = RoundedCornerShape(6.dp),
            border = border,
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor
            ),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedOption.value,
                    color = textColor,
                    style = MaterialTheme.typography.bodyLarge
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "dropdown_icon",
                    tint = iconColor
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            data.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOption.value = option
                        expanded = false
                        onSelectionChange(option) // permet de renvoyer la valeur selectionnée
                    }
                )
            }
        }
    }
}