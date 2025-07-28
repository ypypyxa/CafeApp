package com.pivnoydevelopment.cafeapp.features.splash.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pivnoydevelopment.cafeapp.core.util.SessionManager
import com.pivnoydevelopment.cafeapp.features.splash.ui.event.CheckAuth
import com.pivnoydevelopment.cafeapp.features.splash.ui.event.SplashEvent
import com.pivnoydevelopment.cafeapp.features.splash.ui.state.Authorized
import com.pivnoydevelopment.cafeapp.features.splash.ui.state.Loading
import com.pivnoydevelopment.cafeapp.features.splash.ui.state.SplashState
import com.pivnoydevelopment.cafeapp.features.splash.ui.state.Unauthorized
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow<SplashState>(Loading)
    val state: StateFlow<SplashState> = _state.asStateFlow()

    fun onEvent(event: SplashEvent) {
        when (event) {
            is CheckAuth -> checkAuthorization()
        }
    }

    private fun checkAuthorization() {
        viewModelScope.launch {
            val token = sessionManager.getTokenOrNull()
            if (token != null) {
                _state.value = Authorized
            } else {
                _state.value = Unauthorized
            }
        }
    }
}