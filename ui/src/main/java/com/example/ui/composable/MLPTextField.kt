package com.example.ui.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MLPTextField(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    textFieldValue: String,
    onValueChange: (String) -> Unit = {},
    isReadOnly : Boolean = false
) {
    Column(modifier = Modifier.padding(16.dp)) {
        BoldText(title = title)
        TextField(
            modifier = modifier,
            value = textFieldValue,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                errorContainerColor = Color.Red,
                focusedTextColor = MaterialTheme.colorScheme.tertiary,
                unfocusedTextColor = MaterialTheme.colorScheme.tertiary
            ),
            readOnly = isReadOnly
        )
    }
}