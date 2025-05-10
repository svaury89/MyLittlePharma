package com.example.ui.composable

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

@Composable
fun BoldText(@StringRes title: Int, modifier : Modifier =  Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = title),
        color = MaterialTheme.colorScheme.tertiary,
        fontWeight = FontWeight.Bold
    )
}