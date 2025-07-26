package com.pivnoydevelopment.cafeapp.features.locations.data.dto

import com.pivnoydevelopment.cafeapp.features.locations.data.dto.PointDto

data class LocationDto(
    val id: Int,
    val name: String,
    val point: PointDto
)