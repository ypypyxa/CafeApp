package com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.state

import com.pivnoydevelopment.cafeapp.features.locations.domain.model.Location as CafeLocation

data class CoffeeListState(
    val isLoading: Boolean = false,
    val locations: List<CafeLocation> = emptyList(),
    val userLatitude: Double? = null,
    val userLongitude: Double? = null,
    val showPermissionDialog: Boolean = false,
    val errorMessage: String? = null,
    val showLogoutDialog: Boolean = false,
    val logout: Boolean = false
)