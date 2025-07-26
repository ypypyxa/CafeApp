package com.pivnoydevelopment.cafeapp.features.auth.domain.usecase

import com.pivnoydevelopment.cafeapp.features.auth.domain.model.AuthData
import com.pivnoydevelopment.cafeapp.core.util.NetworkResult

interface RegisterUseCase {
    suspend operator fun invoke(login: String, password: String): NetworkResult<AuthData>
}