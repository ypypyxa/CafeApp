package com.pivnoydevelopment.cafeapp.data.util

import com.pivnoydevelopment.cafeapp.data.dto.LocationDto
import com.pivnoydevelopment.cafeapp.data.dto.MenuItemDto
import com.pivnoydevelopment.cafeapp.data.dto.PointDto
import com.pivnoydevelopment.cafeapp.data.dto.response.AuthResponse
import com.pivnoydevelopment.cafeapp.domain.model.AuthData
import com.pivnoydevelopment.cafeapp.domain.model.Location
import com.pivnoydevelopment.cafeapp.domain.model.MenuItem
import com.pivnoydevelopment.cafeapp.domain.model.Point

fun AuthResponse.toAuthData(): AuthData {
    return AuthData(token, tokenLifeTime)
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