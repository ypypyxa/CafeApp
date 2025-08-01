package com.pivnoydevelopment.cafeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pivnoydevelopment.cafeapp.features.auth.ui.login.screen.LoginScreen
import com.pivnoydevelopment.cafeapp.features.auth.ui.register.screen.RegisterScreen
import com.pivnoydevelopment.cafeapp.features.cart.ui.CartScreen
import com.pivnoydevelopment.cafeapp.features.locations.ui.CoffeeListScreen.screen.CoffeeListScreen
import com.pivnoydevelopment.cafeapp.features.locations.ui.CoffeeMapScreen.CoffeeMapScreen
import com.pivnoydevelopment.cafeapp.features.menu.ui.MenuScreen
import com.pivnoydevelopment.cafeapp.features.splash.ui.screen.SplashScreen

@Composable
fun NavGraph(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("coffee_list") {
            CoffeeListScreen()
//            LocationsScreen(navController)
        }
        composable("coffee_map") {
            CoffeeMapScreen()
//            CoffeeMapScreen(navController)
        }
        composable("menu") {
            MenuScreen()
//            MenuScreen(navController)
        }
        composable("cart") {
            CartScreen()
//            CartScreen(navController)
        }
    }
}