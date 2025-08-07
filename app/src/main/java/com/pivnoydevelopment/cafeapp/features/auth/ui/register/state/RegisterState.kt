package com.pivnoydevelopment.cafeapp.features.auth.ui.register.state

data class RegisterState(
    val login: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val loginError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isButtonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val navigateToCoffeeList: Boolean = false
)