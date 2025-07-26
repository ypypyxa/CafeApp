package com.pivnoydevelopment.cafeapp.features.menu.domain.model

data class MenuItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Double
)