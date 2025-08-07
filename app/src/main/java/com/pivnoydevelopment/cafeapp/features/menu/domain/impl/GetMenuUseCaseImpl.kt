package com.pivnoydevelopment.cafeapp.features.menu.domain.impl

import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.core.domain.network.api.CoffeeRepository
import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem
import com.pivnoydevelopment.cafeapp.features.menu.domain.usecase.GetMenuUseCase

class GetMenuUseCaseImpl(
    private val repository: CoffeeRepository
) : GetMenuUseCase {
    override suspend fun invoke(token: String, locationId: Int): NetworkResult<List<MenuItem>> {
        return repository.getMenu(token, locationId)
    }
}