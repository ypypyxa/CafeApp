package com.pivnoydevelopment.cafeapp.features.auth.ui.login.state

data class LoginState(
    val login: String = "",
    val password: String = "",
    val loginError: String? = null,
    val passwordError: String? = null,
    val isButtonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val navigateToCoffeeList: Boolean = false
)