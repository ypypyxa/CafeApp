package com.pivnoydevelopment.cafeapp.features.locations.ui.coffeemap.state

import com.pivnoydevelopment.cafeapp.features.locations.domain.model.Location

data class CoffeeMapState(
    val markers: List<Location> = emptyList(),
    val error: String? = null
)