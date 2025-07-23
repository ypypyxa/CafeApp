package com.pivnoydevelopment.cafeapp.domain.impl

import com.pivnoydevelopment.cafeapp.data.util.NetworkResult
import com.pivnoydevelopment.cafeapp.domain.api.CoffeeRepository
import com.pivnoydevelopment.cafeapp.domain.usecase.LoginUseCase
import com.pivnoydevelopment.cafeapp.domain.model.AuthData

class LoginUseCaseImpl(
    private val repository: CoffeeRepository
) : LoginUseCase {
    override suspend fun invoke(login: String, password: String): NetworkResult<AuthData> {
        return repository.login(login, password)
    }
}