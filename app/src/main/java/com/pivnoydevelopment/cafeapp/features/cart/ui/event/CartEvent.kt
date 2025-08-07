package com.pivnoydevelopment.cafeapp.features.cart.ui.event

import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem

sealed class CartEvent {
    data class LoadCart(val id: Int) : CartEvent()

    data class AddItem(
        val locationId: Int,
        val locationName: String,
        val item: MenuItem
    ) : CartEvent()

    data class RemoveItem(
        val locationId: Int,
        val item: MenuItem
    ) : CartEvent()

    data class SubmitOrder(val locationId: Int) : CartEvent()
}