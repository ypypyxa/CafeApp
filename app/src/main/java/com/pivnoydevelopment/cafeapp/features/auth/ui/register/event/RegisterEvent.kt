package com.pivnoydevelopment.cafeapp.features.auth.ui.register.event

sealed class RegisterEvent {
    data class LoginChanged(val login: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterEvent()
    object Submit : RegisterEvent()
}