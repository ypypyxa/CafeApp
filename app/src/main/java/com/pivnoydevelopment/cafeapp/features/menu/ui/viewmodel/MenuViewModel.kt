package com.pivnoydevelopment.cafeapp.features.menu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pivnoydevelopment.cafeapp.R
import com.pivnoydevelopment.cafeapp.core.domain.db.api.CartInteractor
import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.core.util.ResourceProvider
import com.pivnoydevelopment.cafeapp.core.util.SessionManager
import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem
import com.pivnoydevelopment.cafeapp.features.menu.domain.usecase.GetMenuUseCase
import com.pivnoydevelopment.cafeapp.features.menu.ui.event.MenuEvent
import com.pivnoydevelopment.cafeapp.features.menu.ui.state.MenuState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getMenuUseCase: GetMenuUseCase,
    private val sessionManager: SessionManager,
    private val cartUseCase: CartInteractor,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _state = MutableStateFlow(MenuState())
    val state = _state.asStateFlow()

    fun onEvent(event: MenuEvent) {
        when (event) {
            is MenuEvent.LoadMenu -> {
                loadMenu(event)
            }
            is MenuEvent.AddItem -> {
                addItemToCart(event.locationId, event.locationName, event.item)
            }
            is MenuEvent.RemoveItem -> {
                removeItemFromCart(event.locationId, event.item)
            }
        }
    }

    private fun loadMenu(event: MenuEvent.LoadMenu) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val token = sessionManager.getTokenOrNull()
            if (token != null) {
                when (val result = getMenuUseCase(token, event.id)) {
                    is NetworkResult.Success -> {
                        _state.update {
                            it.copy(isLoading = false, menuItems = result.data)
                        }
                        syncCartCounts(event.id)
                    }
                    is NetworkResult.Unauthorized -> {
                        //TODO сделать автоматический выход
                        _state.update {
                            it.copy(isLoading = false, errorMessage = resourceProvider.getString(R.string.not_logged_in))
                        }
                    }
                    is NetworkResult.Error -> {
                        _state.update {
                            it.copy(isLoading = false, errorMessage = result.message)
                        }
                    }
                    else -> {
                        _state.update {
                            it.copy(isLoading = false, errorMessage = resourceProvider.getString(R.string.unknown_error))
                        }
                    }
                }
            } else {
                _state.update { it.copy(isLoading = false, errorMessage = resourceProvider.getString(R.string.not_logged_in)) }
            }
        }
    }

    private fun addItemToCart(
        locationId: Int,
        locationName: String,
        item: MenuItem
    ) {
        updateItemCount(item.id, 1)
        viewModelScope.launch {
            cartUseCase.addItem(
                locationId = locationId,
                locationName = locationName,
                menuItem = item.copy(count = getItemCount(item.id) + 1)
            )
            cartUseCase.changeItemCount(locationId, item.id, getItemCount(item.id))
        }
    }

    private fun removeItemFromCart(
        locationId: Int,
        item: MenuItem
    ) {
        updateItemCount(item.id, -1)
        viewModelScope.launch {
            val newCount = getItemCount(item.id)
            cartUseCase.changeItemCount(locationId, item.id, newCount)
            if (newCount == 0) {
                cartUseCase.deleteZeroCountItems()
            }
        }
    }

    private fun updateItemCount(itemId: Int, delta: Int) {
        _state.update { state ->
            val updated = state.menuItems.map {
                if (it.id == itemId) it.copy(count = (it.count + delta).coerceAtLeast(0))
                else it
            }
            state.copy(menuItems = updated)
        }
    }

    private fun getItemCount(itemId: Int): Int {
        return _state.value.menuItems.find { it.id == itemId }?.count ?: 0
    }

    private fun syncCartCounts(locationId: Int) {
        viewModelScope.launch {
            val cartItems = cartUseCase.getLocationCart(locationId)
            _state.update { state ->
                val updatedMenu = state.menuItems.map { menuItem ->
                    val cartItem = cartItems.find { it.id == menuItem.id }
                    menuItem.copy(count = cartItem?.count ?: 0)
                }
                state.copy(menuItems = updatedMenu)
            }
        }
    }
}