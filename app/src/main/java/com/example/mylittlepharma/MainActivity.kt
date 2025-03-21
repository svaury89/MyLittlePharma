package com.example.mylittlepharma

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.MyLittlePharmaTheme
import com.example.ui.composable.AddOrUpdateProductScreen
import com.example.ui.composable.HomeScreen
import com.example.ui.composable.ScanProductScreen
import com.example.ui.navigation.AddOrUpdateProductNavigation
import com.example.ui.navigation.HomeScreenNavigation
import com.example.ui.navigation.ScanProductNavigation


class MainActivity : ComponentActivity() {

    private val cameraPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Implement camera related  code
            } else {
                // Camera permission denied
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) -> {
                // Camera permission already granted
                // Implement camera related code
            }
            else -> {
                cameraPermissionRequest.launch( android.Manifest.permission.CAMERA)
            }
        }

        setContent {
            MyLittlePharmaTheme {
                val navController = rememberNavController()
                NavHost(navController = navController , startDestination = HomeScreenNavigation ){
                    composable<HomeScreenNavigation> {
                        HomeScreen(navController = navController)
                    }
                    composable<AddOrUpdateProductNavigation> {
                        AddOrUpdateProductScreen(navController = navController)
                    }
                    composable<ScanProductNavigation> {
                        ScanProductScreen(navigationController = navController)
                    }
                }
            }
        }
    }


}

