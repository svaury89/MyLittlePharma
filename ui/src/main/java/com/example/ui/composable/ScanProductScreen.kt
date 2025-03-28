package com.example.ui.composable

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun ScanProductScreen(
    navigationController: NavHostController
) {
    CameraScreen(navigationController)
}

