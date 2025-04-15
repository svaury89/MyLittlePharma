package com.example.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

@Immutable
data class CustomColorsPalette(
    val expireSoonColorContainer : Color  = Color.Unspecified,
    val expireSoonColor : Color  = Color.Unspecified


)

val LightCustomColorsPalette = CustomColorsPalette(
    expireSoonColorContainer  = LightOrange,
    expireSoonColor = DarkOrange


)

val DarkCustomColorsPalette = CustomColorsPalette(
    expireSoonColorContainer = LightOrange,
    expireSoonColor = DarkOrange
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }