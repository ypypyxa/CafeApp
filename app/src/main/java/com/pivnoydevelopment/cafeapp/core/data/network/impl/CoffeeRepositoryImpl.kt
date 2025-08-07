package com.pivnoydevelopment.cafeapp.core.data.network.impl

import com.pivnoydevelopment.cafeapp.R
import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.core.data.network.mapper.toAuthData
import com.pivnoydevelopment.cafeapp.core.data.network.mapper.toLocation
import com.pivnoydevelopment.cafeapp.core.data.network.mapper.toMenuItem
import com.pivnoydevelopment.cafeapp.core.data.network.NetworkClient
import com.pivnoydevelopment.cafeapp.core.domain.network.api.CoffeeRepository
import com.pivnoydevelopment.cafeapp.core.util.ResourceProvider
import com.pivnoydevelopment.cafeapp.features.auth.domain.model.AuthData
import com.pivnoydevelopment.cafeapp.features.locations.domain.model.Location
import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem

class CoffeeRepositoryImpl(
    private val api: NetworkClient,
    private val resourceProvider: ResourceProvider
) : CoffeeRepository {

    companion object {
        private const val ERROR_CODE = -1
    }

    override suspend fun login(login: String, password: String): NetworkResult<AuthData> {
        return when (val response = api.login(login, password)) {
            is NetworkResult.Success -> NetworkResult.Success(response.data.toAuthData(login))
            is NetworkResult.BadRequest -> NetworkResult.BadRequest
            is NetworkResult.NotFound -> NetworkResult.NotFound
            is NetworkResult.Error -> NetworkResult.Error(response.code, response.message)
            else -> NetworkResult.Error(ERROR_CODE, resourceProvider.getString(R.string.unknown_error))
        }
    }

    override suspend fun register(login: String, password: String): NetworkResult<AuthData> {
        return when (val response = api.register(login, password)) {
            is NetworkResult.Success -> NetworkResult.Success(response.data.toAuthData(login))
            is NetworkResult.BadRequest -> NetworkResult.BadRequest
            is NetworkResult.LoginAlreadyExists -> NetworkResult.LoginAlreadyExists
            is NetworkResult.Error -> NetworkResult.Error(response.code, response.message)
            else -> NetworkResult.Error(ERROR_CODE, resourceProvider.getString(R.string.unknown_error))
        }
    }

    override suspend fun getLocations(token: String): NetworkResult<List<Location>> {
        return when (val response = api.getLocations(token)) {
            is NetworkResult.Success -> NetworkResult.Success(
                response.data.locations.map { it.toLocation() }
            )
            is NetworkResult.Unauthorized -> NetworkResult.Unauthorized
            is NetworkResult.Error -> NetworkResult.Error(response.code, response.message)
            else -> NetworkResult.Error(ERROR_CODE, resourceProvider.getString(R.string.unknown_error))
        }
    }

    override suspend fun getMenu(token: String, locationId: Int): NetworkResult<List<MenuItem>> {
        return when (val response = api.getMenu(token, locationId)) {
            is NetworkResult.Success -> NetworkResult.Success(
                response.data.menu.map { it.toMenuItem() }
            )
            is NetworkResult.Unauthorized -> NetworkResult.Unauthorized
            is NetworkResult.Error -> NetworkResult.Error(response.code, response.message)
            else -> NetworkResult.Error(ERROR_CODE, resourceProvider.getString(R.string.unknown_error))
        }
    }
}