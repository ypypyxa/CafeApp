package com.pivnoydevelopment.cafeapp.core.domain.network.api

import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.features.auth.domain.model.AuthData
import com.pivnoydevelopment.cafeapp.features.locations.domain.model.Location
import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem

interface CoffeeRepository {
    suspend fun login(login: String, password: String): NetworkResult<AuthData>
    suspend fun register(login: String, password: String): NetworkResult<AuthData>
    suspend fun getLocations(token: String): NetworkResult<List<Location>>
    suspend fun getMenu(token: String, locationId: Int): NetworkResult<List<MenuItem>>
}