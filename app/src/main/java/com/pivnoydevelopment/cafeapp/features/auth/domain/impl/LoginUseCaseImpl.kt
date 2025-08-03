package com.pivnoydevelopment.cafeapp.features.auth.domain.impl

import com.pivnoydevelopment.cafeapp.core.domain.network.api.CoffeeRepository
import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.features.auth.domain.usecase.LoginUseCase
import com.pivnoydevelopment.cafeapp.features.auth.domain.model.AuthData
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val repository: CoffeeRepository
) : LoginUseCase {
    override suspend fun invoke(login: String, password: String): NetworkResult<AuthData> {
        return repository.login(login, password)
    }
}