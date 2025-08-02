package com.pivnoydevelopment.cafeapp.core.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.pivnoydevelopment.cafeapp.navigation.NavGraph

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavGraph(navController = navController)
}