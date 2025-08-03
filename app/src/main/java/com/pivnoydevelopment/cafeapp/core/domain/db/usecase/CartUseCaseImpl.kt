package com.pivnoydevelopment.cafeapp.core.domain.db.usecase

import com.pivnoydevelopment.cafeapp.core.domain.db.api.CartInteractor
import com.pivnoydevelopment.cafeapp.core.domain.db.api.CartRepository
import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem

class CartUseCaseImpl(
    private val repository: CartRepository
) : CartInteractor {

    override suspend fun addItem(locationId: Int, locationName: String?, menuItem: MenuItem) {
        repository.addOrUpdateItem(locationId, locationName, menuItem)
    }

    override suspend fun changeItemCount(locationId: Int, menuItemId: Int, count: Int) {
        repository.updateItemCount(locationId, menuItemId, count)
        if (count == 0) repository.deleteZeroCountItems()
    }

    override suspend fun getItemCount(locationId: Int, menuItemId: Int): Int {
        return repository.getItemCount(locationId, menuItemId)
    }

    override suspend fun getLocationCart(locationId: Int): List<MenuItem> {
        return repository.getItems(locationId)
    }

    override suspend fun clearLocationCart(locationId: Int) {
        repository.deleteLocationItems(locationId)
    }

    override suspend fun deleteZeroCountItems() {
        repository.deleteZeroCountItems()
    }

    override suspend fun clearAll() {
        repository.clearCart()
    }
}