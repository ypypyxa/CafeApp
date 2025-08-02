package com.pivnoydevelopment.cafeapp.features.menu.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MenuItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
    val count: Int = 0
)