package com.pivnoydevelopment.cafeapp.domain.usecase

import com.pivnoydevelopment.cafeapp.data.util.NetworkResult
import com.pivnoydevelopment.cafeapp.domain.model.AuthData

interface RegisterUseCase {
    suspend operator fun invoke(login: String, password: String): NetworkResult<AuthData>
}