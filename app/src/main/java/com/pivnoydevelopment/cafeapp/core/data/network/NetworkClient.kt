package com.pivnoydevelopment.cafeapp.core.data.network

import com.pivnoydevelopment.cafeapp.features.auth.data.dto.AuthResponse
import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.features.locations.data.dto.LocationsResponse
import com.pivnoydevelopment.cafeapp.features.menu.data.dto.MenuResponse

interface NetworkClient {
    suspend fun login(login: String, password: String): NetworkResult<AuthResponse>
    suspend fun register(login: String, password: String): NetworkResult<AuthResponse>
    suspend fun getLocations(token: String): NetworkResult<LocationsResponse>
    suspend fun getMenu(token: String, locationId: Int): NetworkResult<MenuResponse>
}