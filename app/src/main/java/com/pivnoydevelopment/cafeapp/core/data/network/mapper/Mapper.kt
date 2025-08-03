package com.pivnoydevelopment.cafeapp.core.data.network.mapper

import com.pivnoydevelopment.cafeapp.features.locations.data.dto.LocationDto
import com.pivnoydevelopment.cafeapp.features.menu.data.dto.MenuItemDto
import com.pivnoydevelopment.cafeapp.features.locations.data.dto.PointDto
import com.pivnoydevelopment.cafeapp.features.auth.data.dto.AuthResponse
import com.pivnoydevelopment.cafeapp.features.auth.domain.model.AuthData
import com.pivnoydevelopment.cafeapp.features.locations.domain.model.Location
import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem
import com.pivnoydevelopment.cafeapp.features.locations.domain.model.Point

fun AuthResponse.toAuthData(login: String): AuthData {
    return AuthData(
        login = login,
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