package com.pivnoydevelopment.cafeapp.features.locations.ui.coffeemap.viewmodel

import androidx.lifecycle.ViewModel
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeemap.event.CoffeeMapEvent
import com.pivnoydevelopment.cafeapp.features.locations.ui.coffeemap.state.CoffeeMapState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CoffeeMapViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(CoffeeMapState())
    val uiState: StateFlow<CoffeeMapState> = _uiState.asStateFlow()

    fun onEvent(event: CoffeeMapEvent) {
        when (event) {
            is CoffeeMapEvent.AddMarkers -> {
                _uiState.update { it.copy(markers = event.locations) }
            }
        }
    }
}