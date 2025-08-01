package com.pivnoydevelopment.cafeapp.core.data.impl

import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.core.data.mapper.toAuthData
import com.pivnoydevelopment.cafeapp.core.data.mapper.toLocation
import com.pivnoydevelopment.cafeapp.core.data.mapper.toMenuItem
import com.pivnoydevelopment.cafeapp.core.data.network.NetworkClient
import com.pivnoydevelopment.cafeapp.core.domain.api.CoffeeRepository
import com.pivnoydevelopment.cafeapp.features.auth.domain.model.AuthData
import com.pivnoydevelopment.cafeapp.features.locations.domain.model.Location
import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem
import javax.inject.Inject

class CoffeeRepositoryImpl @Inject constructor(
    private val api: NetworkClient
) : CoffeeRepository {

    override suspend fun login(login: String, password: String): NetworkResult<AuthData> {
        return when (val response = api.login(login, password)) {
            is NetworkResult.Success -> NetworkResult.Success(response.data.toAuthData())
            is NetworkResult.BadRequest -> NetworkResult.BadRequest
            is NetworkResult.NotFound -> NetworkResult.NotFound
            is NetworkResult.Error -> NetworkResult.Error(response.code, response.message)
            else -> NetworkResult.Error(-1, "Unknown error")
        }
    }

    override suspend fun register(login: String, password: String): NetworkResult<AuthData> {
        return when (val response = api.register(login, password)) {
            is NetworkResult.Success -> NetworkResult.Success(response.data.toAuthData())
            is NetworkResult.BadRequest -> NetworkResult.BadRequest
            is NetworkResult.LoginAlreadyExists -> NetworkResult.LoginAlreadyExists
            is NetworkResult.Error -> NetworkResult.Error(response.code, response.message)
            else -> NetworkResult.Error(-1, "Unknown error")
        }
    }

    override suspend fun getLocations(token: String): NetworkResult<List<Location>> {
        return when (val response = api.getLocations(token)) {
            is NetworkResult.Success -> NetworkResult.Success(
                response.data.locations.map { it.toLocation() }
            )
            is NetworkResult.Unauthorized -> NetworkResult.Unauthorized
            is NetworkResult.Error -> NetworkResult.Error(response.code, response.message)
            else -> NetworkResult.Error(-1, "Unknown error")
        }
    }

    override suspend fun getMenu(token: String, locationId: Int): NetworkResult<List<MenuItem>> {
        return when (val response = api.getMenu(token, locationId)) {
            is NetworkResult.Success -> NetworkResult.Success(
                response.data.menu.map { it.toMenuItem() }
            )
            is NetworkResult.Unauthorized -> NetworkResult.Unauthorized
            is NetworkResult.Error -> NetworkResult.Error(response.code, response.message)
            else -> NetworkResult.Error(-1, "Unknown error")
        }
    }
}