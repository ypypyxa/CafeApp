package com.pivnoydevelopment.cafeapp.features.menu.ui.event

import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem

sealed class MenuEvent {
    data class LoadMenu(val id: Int) : MenuEvent()
    data class AddItem(val item: MenuItem) : MenuEvent()
    data class RemoveItem(val item: MenuItem) : MenuEvent()
}