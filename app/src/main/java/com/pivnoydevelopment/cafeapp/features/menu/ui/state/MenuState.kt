package com.pivnoydevelopment.cafeapp.features.menu.ui.state

import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem

data class MenuState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val menuItems: List<MenuItem> = emptyList()
)