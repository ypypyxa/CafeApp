package com.pivnoydevelopment.cafeapp.features.auth.domain.impl

import com.pivnoydevelopment.cafeapp.core.domain.api.CoffeeRepository
import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.features.auth.domain.model.AuthData
import com.pivnoydevelopment.cafeapp.features.auth.domain.usecase.RegisterUseCase
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val repository: CoffeeRepository
) : RegisterUseCase {
    override suspend fun invoke(login: String, password: String): NetworkResult<AuthData> {
        return repository.register(login, password)
    }
}