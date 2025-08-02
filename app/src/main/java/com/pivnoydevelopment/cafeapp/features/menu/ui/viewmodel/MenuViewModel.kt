package com.pivnoydevelopment.cafeapp.features.menu.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.core.util.SessionManager
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
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(MenuState())
    val state = _state.asStateFlow()

    fun onEvent(event: MenuEvent) {
        when (event) {
            is MenuEvent.LoadMenu -> {
                loadMenu(event)
            }
            is MenuEvent.AddItem -> {
                updateItemCount(event.item.id, 1)
            }
            is MenuEvent.RemoveItem -> {
                updateItemCount(event.item.id, -1)
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
                    }
                    is NetworkResult.Unauthorized -> {
                        _state.update {
                            it.copy(isLoading = false, errorMessage = "Не авторизован")
                        }
                    }
                    is NetworkResult.Error -> {
                        _state.update {
                            it.copy(isLoading = false, errorMessage = result.message)
                        }
                    }
                    else -> {
                        _state.update {
                            it.copy(isLoading = false, errorMessage = "Неизвестная ошибка")
                        }
                    }
                }
            } else {
                _state.update { it.copy(isLoading = false, errorMessage = "Нет токена") }
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
}