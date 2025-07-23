package com.pivnoydevelopment.cafeapp.domain.impl

import com.pivnoydevelopment.cafeapp.data.util.NetworkResult
import com.pivnoydevelopment.cafeapp.domain.api.CoffeeRepository
import com.pivnoydevelopment.cafeapp.domain.model.MenuItem
import com.pivnoydevelopment.cafeapp.domain.usecase.GetMenuUseCase

class GetMenuUseCaseImpl(
    private val repository: CoffeeRepository
) : GetMenuUseCase {
    override suspend fun invoke(token: String, locationId: Int): NetworkResult<List<MenuItem>> {
        return repository.getMenu(token, locationId)
    }
}