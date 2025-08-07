package com.pivnoydevelopment.cafeapp.features.auth.ui.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pivnoydevelopment.cafeapp.R
import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.core.util.ResourceProvider
import com.pivnoydevelopment.cafeapp.core.util.SessionManager
import com.pivnoydevelopment.cafeapp.features.auth.domain.usecase.RegisterUseCase
import com.pivnoydevelopment.cafeapp.features.auth.ui.register.event.RegisterEvent
import com.pivnoydevelopment.cafeapp.features.auth.ui.register.state.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val sessionManager: SessionManager,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    private val emailPattern = android.util.Patterns.EMAIL_ADDRESS
    private val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}\$")

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.LoginChanged -> {
                _state.update { it.copy(login = event.login) }
                validate()
            }
            is RegisterEvent.PasswordChanged -> {
                _state.update { it.copy(password = event.password) }
                validate()
            }
            is RegisterEvent.ConfirmPasswordChanged -> {
                _state.update { it.copy(confirmPassword = event.confirmPassword) }
                validate()
            }
            is RegisterEvent.Submit -> {
                submitRegistration()
            }
        }
    }

    private fun validate() {
        val login = _state.value.login
        val password = _state.value.password
        val confirmPassword = _state.value.confirmPassword

        val loginError = if (login.isNotEmpty() && !emailPattern.matcher(login).matches())
            resourceProvider.getString(R.string.invalid_email)
        else null

        val passwordError = if (password.isNotEmpty() && !passwordPattern.matches(password))
            resourceProvider.getString(R.string.invalid_password)
        else null

        val confirmPasswordError = if (confirmPassword.isNotEmpty() && confirmPassword != password)
            resourceProvider.getString(R.string.confirm_password_error)
        else null

        _state.update {
            it.copy(
                loginError = loginError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError,
                isButtonEnabled = login.isNotEmpty() &&
                        password.isNotEmpty() &&
                        confirmPassword.isNotEmpty() &&
                        loginError == null &&
                        passwordError == null &&
                        confirmPasswordError == null
            )
        }
    }

    private fun submitRegistration() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = registerUseCase.invoke(
                _state.value.login,
                _state.value.password
            )

            when (result) {
                is NetworkResult.Success -> {
                    sessionManager.saveAuthData(result.data)
                    _state.update { it.copy(isLoading = false, navigateToCoffeeList = true) }
                }
                is NetworkResult.LoginAlreadyExists -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            loginError = resourceProvider.getString(R.string.login_exist)
                        )
                    }
                }
                is NetworkResult.BadRequest -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            loginError = resourceProvider.getString(R.string.bad_request)
                        )
                    }
                }
                is NetworkResult.Error -> {
                    _state.update {
                        val errorPrefix = resourceProvider.getString(R.string.error_prefix)
                        val errorMessage = result.message ?: resourceProvider.getString(R.string.unknown_error)
                        it.copy(
                            isLoading = false,
                            loginError = "$errorPrefix $errorMessage"
                        )
                    }
                }
                else -> {
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}