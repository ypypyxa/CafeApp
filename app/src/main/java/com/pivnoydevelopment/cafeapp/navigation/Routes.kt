package com.pivnoydevelopment.cafeapp.navigation

import kotlinx.serialization.Serializable

@Serializable
object Splash

@Serializable
object Login

@Serializable
object Register

@Serializable
object CoffeeList

@Serializable
object CoffeeMap

@Serializable
data class Menu(
    val id: Int,
    val name: String
)

@Serializable
data class Cart(
    val id: Int,
    val name: String
)