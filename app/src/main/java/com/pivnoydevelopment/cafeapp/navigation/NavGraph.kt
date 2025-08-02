package com.pivnoydevelopment.cafeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.pivnoydevelopment.cafeapp.features.auth.ui.login.screen.LoginScreen
import com.pivnoydevelopment.cafeapp.features.auth.ui.register.screen.RegisterScreen
import com.pivnoydevelopment.cafeapp.features.cart.ui.CartScreen
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.screen.CoffeeListScreen
import com.pivnoydevelopment.cafeapp.features.locations.ui.CoffeeMapScreen.CoffeeMapScreen
import com.pivnoydevelopment.cafeapp.features.menu.ui.MenuScreen
import com.pivnoydevelopment.cafeapp.features.splash.ui.screen.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Splash
    ) {
        composable<Splash> {
            SplashScreen(navController)
        }
        composable<Login> {
            LoginScreen(navController)
        }
        composable<Register> {
            RegisterScreen(navController)
        }
        composable<CoffeeList> {
            CoffeeListScreen(navController = navController)
        }
        composable<CoffeeMap> {
            CoffeeMapScreen()
//            CoffeeMapScreen(navController)
        }
        composable<Menu> { backStackEntry ->
            val menu = backStackEntry.toRoute<Menu>()
            MenuScreen(navController = navController, id = menu.id)
        }
        composable<Cart> {
            CartScreen()
//            CartScreen(navController)
        }
    }
}