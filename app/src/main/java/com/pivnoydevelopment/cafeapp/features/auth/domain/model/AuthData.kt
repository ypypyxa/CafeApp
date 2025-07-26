package com.pivnoydevelopment.cafeapp.features.auth.domain.model

data class AuthData(
    val token: String,
    val tokenLifeTime: Long,
    val savedAt: Long
)