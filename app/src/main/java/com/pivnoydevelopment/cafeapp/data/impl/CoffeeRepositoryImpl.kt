package com.pivnoydevelopment.cafeapp.data.impl

import com.pivnoydevelopment.cafeapp.data.dto.request.AuthRequest
import com.pivnoydevelopment.cafeapp.data.network.CoffeeApiService
import com.pivnoydevelopment.cafeapp.data.util.NetworkResult
import com.pivnoydevelopment.cafeapp.data.util.toAuthData
import com.pivnoydevelopment.cafeapp.data.util.toLocation
import com.pivnoydevelopment.cafeapp.data.util.toMenuItem
import com.pivnoydevelopment.cafeapp.domain.api.CoffeeRepository
import com.pivnoydevelopment.cafeapp.domain.model.AuthData
import com.pivnoydevelopment.cafeapp.domain.model.Location
import com.pivnoydevelopment.cafeapp.domain.model.MenuItem

class CoffeeRepositoryImpl(
    private val api: CoffeeApiService
) : CoffeeRepository {

    override suspend fun login(login: String, password: String): NetworkResult<AuthData> {
        return try {
            val response = api.login(AuthRequest(login, password))
            when {
                response.isSuccessful -> NetworkResult.Success(response.body()!!.toAuthData())
                response.code() == 400 -> NetworkResult.BadRequest
                response.code() == 404 -> NetworkResult.NotFound
                else -> NetworkResult.Error(response.code(), response.errorBody()?.string())
            }
        } catch (e: Exception) {
            NetworkResult.Error(-1, e.message)
        }
    }

    override suspend fun register(login: String, password: String): NetworkResult<AuthData> {
        return try {
            val response = api.register(AuthRequest(login, password))
            when {
                response.isSuccessful -> NetworkResult.Success(response.body()!!.toAuthData())
                response.code() == 400 -> NetworkResult.BadRequest
                response.code() == 406 -> NetworkResult.LoginAlreadyExists
                else -> NetworkResult.Error(response.code(), response.errorBody()?.string())
            }
        } catch (e: Exception) {
            NetworkResult.Error(-1, e.message)
        }
    }

    override suspend fun getLocations(token: String): NetworkResult<List<Location>> {
        return try {
            val response = api.getLocations("Bearer $token")
            when {
                response.isSuccessful -> NetworkResult.Success(
                    response.body()!!.locations.map { it.toLocation() }
                )
                response.code() == 401 -> NetworkResult.Unauthorized
                else -> NetworkResult.Error(response.code(), response.errorBody()?.string())
            }
        } catch (e: Exception) {
            NetworkResult.Error(-1, e.message)
        }
    }

    override suspend fun getMenu(token: String, locationId: Int): NetworkResult<List<MenuItem>> {
        return try {
            val response = api.getMenu("Bearer $token", locationId)
            when {
                response.isSuccessful -> NetworkResult.Success(
                    response.body()!!.menu.map { it.toMenuItem() }
                )
                response.code() == 401 -> NetworkResult.Unauthorized
                else -> NetworkResult.Error(response.code(), response.errorBody()?.string())
            }
        } catch (e: Exception) {
            NetworkResult.Error(-1, e.message)
        }
    }
}