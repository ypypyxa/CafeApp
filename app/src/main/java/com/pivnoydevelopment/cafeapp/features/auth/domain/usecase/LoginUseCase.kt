package com.pivnoydevelopment.cafeapp.features.auth.domain.usecase

import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.features.auth.domain.model.AuthData

interface LoginUseCase {
    suspend operator fun invoke(login: String, password: String): NetworkResult<AuthData>
}