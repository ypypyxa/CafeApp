package com.pivnoydevelopment.cafeapp.data.network

import com.pivnoydevelopment.cafeapp.data.dto.response.AuthResponse
import com.pivnoydevelopment.cafeapp.data.dto.response.LocationsResponse
import com.pivnoydevelopment.cafeapp.data.dto.response.MenuResponse
import com.pivnoydevelopment.cafeapp.data.util.NetworkResult

interface NetworkClient {
    suspend fun login(login: String, password: String): NetworkResult<AuthResponse>
    suspend fun register(login: String, password: String): NetworkResult<AuthResponse>
    suspend fun getLocations(token: String): NetworkResult<LocationsResponse>
    suspend fun getMenu(token: String, locationId: Int): NetworkResult<MenuResponse>
}