package com.pivnoydevelopment.cafeapp.domain.impl

import com.pivnoydevelopment.cafeapp.data.util.NetworkResult
import com.pivnoydevelopment.cafeapp.domain.api.CoffeeRepository
import com.pivnoydevelopment.cafeapp.domain.model.AuthData
import com.pivnoydevelopment.cafeapp.domain.usecase.RegisterUseCase

class RegisterUseCaseImpl(
    private val repository: CoffeeRepository
) : RegisterUseCase {
    override suspend fun invoke(login: String, password: String): NetworkResult<AuthData> {
        return repository.register(login, password)
    }
}