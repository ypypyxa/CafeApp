package com.pivnoydevelopment.cafeapp.domain.usecase

import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.core.domain.model.AuthData

interface LoginUseCase {
    suspend operator fun invoke(login: String, password: String): NetworkResult<AuthData>
}