package com.pivnoydevelopment.cafeapp.features.locations.ui.coffeemap.event

import com.pivnoydevelopment.cafeapp.features.locations.domain.model.Location

sealed class CoffeeMapEvent {
    data class AddMarkers(val locations: List<Location>) : CoffeeMapEvent()
}