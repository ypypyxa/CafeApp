package com.pivnoydevelopment.cafeapp.domain.impl

import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.core.domain.api.CoffeeRepository
import com.pivnoydevelopment.cafeapp.domain.model.Location
import com.pivnoydevelopment.cafeapp.domain.usecase.GetLocationsUseCase

class GetLocationsUseCaseImpl(
    private val repository: CoffeeRepository
) : GetLocationsUseCase {
    override suspend fun invoke(token: String): NetworkResult<List<Location>> {
        return repository.getLocations(token)
    }
}