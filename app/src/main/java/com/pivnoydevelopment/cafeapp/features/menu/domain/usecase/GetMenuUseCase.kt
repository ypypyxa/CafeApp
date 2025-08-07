package com.pivnoydevelopment.cafeapp.features.menu.domain.usecase

import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem

interface GetMenuUseCase {
    suspend operator fun invoke(token: String, locationId: Int): NetworkResult<List<MenuItem>>
}