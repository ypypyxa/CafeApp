package com.pivnoydevelopment.cafeapp.core.data.mapper

import com.pivnoydevelopment.cafeapp.data.dto.LocationDto
import com.pivnoydevelopment.cafeapp.data.dto.MenuItemDto
import com.pivnoydevelopment.cafeapp.data.dto.PointDto
import com.pivnoydevelopment.cafeapp.core.data.dto.response.AuthResponse
import com.pivnoydevelopment.cafeapp.core.domain.model.AuthData
import com.pivnoydevelopment.cafeapp.domain.model.Location
import com.pivnoydevelopment.cafeapp.domain.model.MenuItem
import com.pivnoydevelopment.cafeapp.domain.model.Point

fun AuthResponse.toAuthData(): AuthData {
    return AuthData(
        token = token,
        tokenLifeTime = tokenLifeTime,
        savedAt = System.currentTimeMillis()
    )
}

fun PointDto.toPoint(): Point {
    return Point(latitude, longitude)
}

fun LocationDto.toLocation(): Location {
    return Location(id, name, point.toPoint())
}

fun MenuItemDto.toMenuItem(): MenuItem {
    return MenuItem(id, name, imageURL, price)
}