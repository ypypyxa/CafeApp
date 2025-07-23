package com.pivnoydevelopment.cafeapp.domain.usecase

import com.pivnoydevelopment.cafeapp.data.util.NetworkResult
import com.pivnoydevelopment.cafeapp.domain.model.Location

interface GetLocationsUseCase {
    suspend operator fun invoke(token: String): NetworkResult<List<Location>>
}