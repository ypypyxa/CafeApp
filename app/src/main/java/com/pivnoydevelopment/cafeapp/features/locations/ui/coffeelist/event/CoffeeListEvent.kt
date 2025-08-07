package com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.event

sealed class CoffeeListEvent {
    object LoadLocations : CoffeeListEvent()
    object RequestLocationPermission : CoffeeListEvent()
    data class OnPermissionResult(val granted: Boolean) : CoffeeListEvent()
    object OpenSettings : CoffeeListEvent()
    data class UpdateUserLocation(val latitude: Double, val longitude: Double) : CoffeeListEvent()
    object DismissPermissionDialog : CoffeeListEvent()
    object Logout : CoffeeListEvent()
    object LogoutDialog : CoffeeListEvent()
    object DismissLogoutDialog : CoffeeListEvent()
}