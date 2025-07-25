package com.pivnoydevelopment.cafeapp.core.data.dto.response

data class AuthResponse(
    val token: String,
    val tokenLifeTime: Long
)