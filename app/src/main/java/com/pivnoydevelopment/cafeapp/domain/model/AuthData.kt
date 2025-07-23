package com.pivnoydevelopment.cafeapp.domain.model

data class AuthData(
    val token: String,
    val tokenLifeTime: Long
)