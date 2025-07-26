package com.pivnoydevelopment.cafeapp.core.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable
fun MainScreen(innerPadding: PaddingValues = PaddingValues()) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

    }
}