package com.pivnoydevelopment.cafeapp.data.dto.response

data class AuthResponse(
    val token: String,
    val tokenLifeTime: Long
)