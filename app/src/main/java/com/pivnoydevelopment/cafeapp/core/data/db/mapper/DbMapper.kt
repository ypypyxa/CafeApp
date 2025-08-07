package com.pivnoydevelopment.cafeapp.core.data.db.mapper

import com.pivnoydevelopment.cafeapp.core.data.db.entity.CartItemEntity
import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem

fun MenuItem.toEntity(userId: String, locationId: Int, locationName: String?) = CartItemEntity(
    userId = userId,
    locationId = locationId,
    locationName = locationName,
    menuItemId = id,
    menuItemName = name,
    imageUrl = imageUrl,
    price = price,
    count = count
)

fun CartItemEntity.toMenuItem(): MenuItem {
    return MenuItem(
        id = menuItemId,
        name = menuItemName,
        imageUrl = imageUrl,
        price = price,
        count = count
    )
}