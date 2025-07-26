package com.pivnoydevelopment.cafeapp.features.auth.data.dto

data class AuthRequest(
    val login: String,
    val password: String
)