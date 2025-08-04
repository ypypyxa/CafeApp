package com.pivnoydevelopment.cafeapp.features.cart.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pivnoydevelopment.cafeapp.core.domain.db.api.CartInteractor
import com.pivnoydevelopment.cafeapp.features.cart.ui.event.CartEvent
import com.pivnoydevelopment.cafeapp.features.cart.ui.state.CartState
import com.pivnoydevelopment.cafeapp.features.menu.domain.model.MenuItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCase: CartInteractor
) : ViewModel() {

    private val _state = MutableStateFlow(CartState())
    val state = _state.asStateFlow()

    fun onEvent(event: CartEvent) {
        when (event) {
            is CartEvent.LoadCart -> {
                loadCart(event.id)
            }
            is CartEvent.AddItem -> {
                addItemToCart(event.locationId, event.locationName, event.item)
            }
            is CartEvent.RemoveItem -> {
                removeItemFromCart(event.locationId, event.item)
            }
            is CartEvent.SubmitOrder -> {
                submitOrder(event.locationId)
            }
        }
    }

    private fun loadCart(id: Int) {
        viewModelScope.launch {
            val items = cartUseCase.getLocationCart(id)
            _state.update { it.copy(
                isLoading = false,
                menuItems = items,
                totalPrice = calculateTotalPrice(items)
            ) }
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

    private fun calculateTotalPrice(items: List<MenuItem>): Int {
        return items.sumOf { (it.price * it.count) }
    }

    private fun getItemCount(itemId: Int): Int {
        return _state.value.menuItems.find { it.id == itemId }?.count ?: 0
    }

    private fun submitOrder(id: Int) {
        viewModelScope.launch {
            cartUseCase.clearLocationCart(id)
            _state.update { it.copy(isSubmiting = true) }
        }
    }

    private fun updateItemCount(itemId: Int, delta: Int) {
        _state.update { state ->
            val updated = state.menuItems.map {
                if (it.id == itemId) it.copy(count = (it.count + delta).coerceAtLeast(0))
                else it
            }
            state.copy(
                menuItems = updated,
                totalPrice = calculateTotalPrice(updated)
            )

        }
    }
}