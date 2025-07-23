package com.pivnoydevelopment.cafeapp.domain.usecase

import com.pivnoydevelopment.cafeapp.data.util.NetworkResult
import com.pivnoydevelopment.cafeapp.domain.model.MenuItem

interface GetMenuUseCase {
    suspend operator fun invoke(token: String, locationId: Int): NetworkResult<List<MenuItem>>
}