package com.pivnoydevelopment.cafeapp.core.domain.db.api

import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem

interface CartRepository {
    suspend fun addOrUpdateItem(locationId: Int, locationName: String?, menuItem: MenuItem)
    suspend fun updateItemCount(locationId: Int, menuItemId: Int, count: Int)
    suspend fun getItemCount(locationId: Int, menuItemId: Int): Int
    suspend fun getItems(locationId: Int): List<MenuItem>
    suspend fun deleteLocationItems(locationId: Int)
    suspend fun deleteZeroCountItems()
    suspend fun clearCart()
}