package com.pivnoydevelopment.cafeapp.core.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import com.pivnoydevelopment.cafeapp.features.auth.data.dto.AuthRequest
import com.pivnoydevelopment.cafeapp.features.auth.data.dto.AuthResponse
import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.features.locations.data.dto.LocationsResponse
import com.pivnoydevelopment.cafeapp.features.menu.data.dto.MenuResponse
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val api: CoffeeApiService,
    private val context: Context
) : NetworkClient {

    override suspend fun login(login: String, password: String): NetworkResult<AuthResponse> {
        if (!isConnected()) {
            return NetworkResult.Error(-1, "No internet connection")
        }

        return try {
            val response = api.login(AuthRequest(login, password))
            when {
                response.isSuccessful -> NetworkResult.Success(response.body()!!)
                response.code() == 400 -> NetworkResult.BadRequest
                response.code() == 404 -> NetworkResult.NotFound
                else -> NetworkResult.Error(response.code(), response.errorBody()?.string())
            }
        } catch (e: Exception) {
            NetworkResult.Error(-1, e.message)
        }
    }

    override suspend fun register(login: String, password: String): NetworkResult<AuthResponse> {
        if (!isConnected()) {
            return NetworkResult.Error(-1, "No internet connection")
        }

        return try {
            val response = api.register(AuthRequest(login, password))
            when {
                response.isSuccessful -> NetworkResult.Success(response.body()!!)
                response.code() == 400 -> NetworkResult.BadRequest
                response.code() == 406 -> NetworkResult.LoginAlreadyExists
                else -> NetworkResult.Error(response.code(), response.errorBody()?.string())
            }
        } catch (e: Exception) {
            NetworkResult.Error(-1, e.message)
        }
    }

    override suspend fun getLocations(token: String): NetworkResult<LocationsResponse> {
        if (!isConnected()) {
            return NetworkResult.Error(-1, "No internet connection")
        }

        return try {
            val response = api.getLocations("Bearer $token")
            when {
                response.isSuccessful -> NetworkResult.Success(response.body()!!)
                response.code() == 401 -> NetworkResult.Unauthorized
                else -> NetworkResult.Error(response.code(), response.errorBody()?.string())
            }
        } catch (e: Exception) {
            NetworkResult.Error(-1, e.message)
        }
    }

    override suspend fun getMenu(token: String, locationId: Int): NetworkResult<MenuResponse> {
        if (!isConnected()) {
            return NetworkResult.Error(-1, "No internet connection")
        }

        return try {
            val response = api.getMenu("Bearer $token", locationId)
            when {
                response.isSuccessful -> NetworkResult.Success(response.body()!!)
                response.code() == 401 -> NetworkResult.Unauthorized
                else -> NetworkResult.Error(response.code(), response.errorBody()?.string())
            }
        } catch (e: Exception) {
            NetworkResult.Error(-1, e.message)
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}