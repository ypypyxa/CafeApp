package com.pivnoydevelopment.cafeapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pivnoydevelopment.cafeapp.features.auth.ui.login.screen.LoginScreen
import com.pivnoydevelopment.cafeapp.features.auth.ui.register.screen.RegisterScreen
import com.pivnoydevelopment.cafeapp.features.cart.ui.screen.CartScreen
import com.pivnoydevelopment.cafeapp.features.locations.domain.model.Location
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.screen.CoffeeListScreen
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeemap.screen.CoffeeMapScreen
import com.pivnoydevelopment.cafeapp.features.menu.ui.screen.MenuScreen
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
            CoffeeListScreen(navController)
        }
        composable<CoffeeMap> { backStackEntry ->
            val coffeeMap = backStackEntry.toRoute<CoffeeMap>()
            val userLocationJson = coffeeMap.userLocation
            val locationsJson = coffeeMap.locations
            val type = object : TypeToken<Pair<Double, Double>>() {}.type
            val userLocation: Pair<Double, Double>? = Gson().fromJson(userLocationJson, type)
            val locations = Gson().fromJson(locationsJson, Array<Location>::class.java).toList()
            CoffeeMapScreen(
                navController = navController,
                userLocation = userLocation,
                locations = locations
            )
        }
        composable<Menu> { backStackEntry ->
            val menu = backStackEntry.toRoute<Menu>()
            MenuScreen(
                navController = navController,
                id = menu.id,
                name = menu.name
            )
        }
        composable<Cart> { backStackEntry ->
            val cart = backStackEntry.toRoute<Cart>()
            CartScreen(
                navController = navController,
                id = cart.id,
                name = cart.name
            )
        }
    }
}