package com.pivnoydevelopment.cafeapp.navigation

sealed class Routes(val route: String) {
    object Splash : Routes("splash")
    object Login : Routes("login")
    object Register : Routes("register")
    object CoffeeList : Routes("coffee_list")
    object Map : Routes("map")
    object Menu : Routes("menu")
    object Cart : Routes("cart")
}