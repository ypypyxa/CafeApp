package com.pivnoydevelopment.cafeapp.features.cart.ui.state

import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem

data class CartState(
    val isLoading: Boolean = false,
    val menuItems: List<MenuItem> = emptyList(),
    val isSubmiting: Boolean = false,
    val totalPrice: Int = 0
)