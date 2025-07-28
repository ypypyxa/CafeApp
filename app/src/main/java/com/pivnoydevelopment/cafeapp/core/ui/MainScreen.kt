package com.pivnoydevelopment.cafeapp.core.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.pivnoydevelopment.cafeapp.navigation.NavGraph
import com.pivnoydevelopment.cafeapp.navigation.Routes

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavGraph(
        navController = navController,
        startDestination = Routes.Splash.route
    )
}