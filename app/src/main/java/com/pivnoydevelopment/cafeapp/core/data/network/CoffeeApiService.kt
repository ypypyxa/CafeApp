package com.pivnoydevelopment.cafeapp.core.data.network

import com.pivnoydevelopment.cafeapp.core.data.dto.request.AuthRequest
import com.pivnoydevelopment.cafeapp.core.data.dto.response.AuthResponse
import com.pivnoydevelopment.cafeapp.data.dto.response.LocationsResponse
import com.pivnoydevelopment.cafeapp.data.dto.response.MenuResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface CoffeeApiService {

    @POST("/auth/login")
    suspend fun login(@Body authRequest: AuthRequest): Response<AuthResponse>

    @POST("/auth/register")
    suspend fun register(@Body authRequest: AuthRequest): Response<AuthResponse>

    @GET("/locations")
    suspend fun getLocations(@Header("Authorization") token: String): Response<LocationsResponse>

    @GET("/location/{id}/menu")
    suspend fun getMenu(
        @Header("Authorization") token: String,
        @Path("id") locationId: Int
    ): Response<MenuResponse>
}