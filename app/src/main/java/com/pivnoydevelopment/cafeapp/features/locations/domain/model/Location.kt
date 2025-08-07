package com.pivnoydevelopment.cafeapp.features.locations.domain.model

data class Location(
    val id: Int,
    val name: String,
    val point: Point
)