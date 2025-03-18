package com.example.mylittlepharma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.MyLittlePharmaTheme
import com.example.ui.composable.AddOrUpdateProductScreen
import com.example.ui.composable.HomeScreen
import com.example.ui.navigation.AddOrUpdateProductNavigation
import com.example.ui.navigation.HomeScreenNavigation


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                }
            }
        }
    }
}
