package com.pivnoydevelopment.cafeapp.core.data.db.impl

import com.pivnoydevelopment.cafeapp.R
import com.pivnoydevelopment.cafeapp.core.data.db.dao.CartDao
import com.pivnoydevelopment.cafeapp.core.data.db.mapper.toEntity
import com.pivnoydevelopment.cafeapp.core.data.db.mapper.toMenuItem
import com.pivnoydevelopment.cafeapp.core.domain.db.api.CartRepository
import com.pivnoydevelopment.cafeapp.core.util.ResourceProvider
import com.pivnoydevelopment.cafeapp.core.util.SessionManager
import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem

class CartRepositoryImpl(
    private val dao: CartDao,
    private val sessionManager: SessionManager,
    private val resourceProvider: ResourceProvider
) : CartRepository {

    private suspend fun getUserId(): String {
        return sessionManager.getLoginOrNull()
            ?: throw IllegalStateException(resourceProvider.getString(R.string.not_logged_in))
    }

    override suspend fun addOrUpdateItem(locationId: Int, locationName: String?, menuItem: MenuItem) {
        val userId = getUserId()
        dao.insertOrUpdate(menuItem.toEntity(userId, locationId, locationName))
    }

    override suspend fun updateItemCount(locationId: Int, menuItemId: Int, count: Int) {
        val userId = getUserId()
        dao.updateCount(userId, locationId, menuItemId, count)
    }

    override suspend fun getItemCount(locationId: Int, menuItemId: Int): Int {
        val userId = getUserId()
        return dao.getCount(userId, locationId, menuItemId) ?: 0
    }

    override suspend fun getItems(locationId: Int): List<MenuItem> {
        val userId = getUserId()
        return dao.getItemsByLocation(userId, locationId).map { it.toMenuItem() }
    }

    override suspend fun deleteLocationItems(locationId: Int) {
        val userId = getUserId()
        dao.deleteItemsByLocation(userId, locationId)
    }

    override suspend fun deleteZeroCountItems() {
        val userId = getUserId()
        dao.deleteZeroCountItems(userId)
    }

    override suspend fun clearCart() {
        val userId = getUserId()
        dao.clearCart(userId)
    }
}