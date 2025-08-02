package com.pivnoydevelopment.cafeapp.navigation

import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem
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
data class Menu(val id: Int)

@Serializable
data class Cart(val items: List<MenuItem>)