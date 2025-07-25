package com.pivnoydevelopment.cafeapp.core.domain.model

data class AuthData(
    val token: String,
    val tokenLifeTime: Long,
    val savedAt: Long
)