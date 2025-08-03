package com.pivnoydevelopment.cafeapp.core.domain.db.api

import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem

interface CartInteractor {
    suspend fun addItem(locationId: Int, locationName: String?, menuItem: MenuItem)
    suspend fun changeItemCount(locationId: Int, menuItemId: Int, count: Int)
    suspend fun getItemCount(locationId: Int, menuItemId: Int): Int
    suspend fun getLocationCart(locationId: Int): List<MenuItem>
    suspend fun clearLocationCart(locationId: Int)
    suspend fun deleteZeroCountItems()
    suspend fun clearAll()
}