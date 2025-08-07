package com.pivnoydevelopment.cafeapp.features.auth.data.dto

data class AuthResponse(
    val token: String,
    val tokenLifeTime: Long
)