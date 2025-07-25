package com.pivnoydevelopment.cafeapp.domain.usecase

import com.pivnoydevelopment.cafeapp.core.domain.model.AuthData
import com.pivnoydevelopment.cafeapp.core.util.NetworkResult

interface RegisterUseCase {
    suspend operator fun invoke(login: String, password: String): NetworkResult<AuthData>
}