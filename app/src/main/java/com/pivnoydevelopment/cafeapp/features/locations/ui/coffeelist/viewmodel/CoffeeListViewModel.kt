package com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.core.util.SessionManager
import com.pivnoydevelopment.cafeapp.features.locations.domain.usecase.GetLocationsUseCase
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.event.CoffeeListEvent
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeelist.state.CoffeeListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoffeeListViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val sessionManager: SessionManager,
    private val locationClient: FusedLocationProviderClient
) : ViewModel() {

    private val _state = MutableStateFlow(CoffeeListState())
    val state = _state.asStateFlow()

    fun onEvent(event: CoffeeListEvent) {
        when (event) {
            is CoffeeListEvent.LoadLocations -> {
                loadLocations()
            }

            is CoffeeListEvent.RequestLocationPermission -> {
                _state.update { it.copy(showPermissionDialog = false) }
            }

            is CoffeeListEvent.OnPermissionResult -> {
                _state.update { it.copy(showPermissionDialog = !event.granted) }
            }

            is CoffeeListEvent.OpenSettings -> {
                _state.update { it.copy(showPermissionDialog = false) }
            }

            is CoffeeListEvent.UpdateUserLocation -> {
                _state.update {
                    it.copy(
                        userLatitude = event.latitude,
                        userLongitude = event.longitude
                    )
                }
            }

            is CoffeeListEvent.DismissPermissionDialog -> {
                _state.value = _state.value.copy(showPermissionDialog = false)
                loadLocations()
            }

            is CoffeeListEvent.Logout -> {
                viewModelScope.launch {
                    sessionManager.logout()
                }
            }

            is CoffeeListEvent.LogoutDialog -> {
                _state.value = _state.value.copy(showLogoutDialog = true)
            }

            is CoffeeListEvent.DismissLogoutDialog -> {
                _state.value = _state.value.copy(showLogoutDialog = false)
            }
        }
    }

    private fun loadLocations() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val token = sessionManager.getTokenOrNull()
            if (token == null) {
                _state.update { it.copy(isLoading = false, errorMessage = "Токен не найден") }
                return@launch
            }

            val result = getLocationsUseCase(token)

            when (result) {
                is NetworkResult.Success -> {
                    _state.update {
                        it.copy(isLoading = false, locations = result.data)
                    }
                }
                is NetworkResult.Unauthorized -> {
                    _state.update {
                        it.copy(isLoading = false, errorMessage = "Не авторизован")
                    }
                }
                is NetworkResult.Error -> {
                    _state.update {
                        it.copy(isLoading = false, errorMessage = result.message)
                    }
                }
                else -> {
                    _state.update {
                        it.copy(isLoading = false, errorMessage = "Неизвестная ошибка")
                    }
                }
            }
        }
    }

    fun fetchLastLocation() {
        viewModelScope.launch {
            try {
                locationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        _state.update {
                            it.copy(
                                userLatitude = location.latitude,
                                userLongitude = location.longitude
                            )
                        }
                        onEvent(CoffeeListEvent.LoadLocations)
                    }
                }
            } catch (e: SecurityException) {
                _state.update { it.copy(errorMessage = e.message) }
            }
        }
    }
}