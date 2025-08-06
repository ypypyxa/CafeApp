package com.pivnoydevelopment.cafeapp.features.auth.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pivnoydevelopment.cafeapp.R
import com.pivnoydevelopment.cafeapp.core.util.NetworkResult
import com.pivnoydevelopment.cafeapp.core.util.ResourceProvider
import com.pivnoydevelopment.cafeapp.core.util.SessionManager
import com.pivnoydevelopment.cafeapp.features.auth.domain.usecase.LoginUseCase
import com.pivnoydevelopment.cafeapp.features.auth.ui.login.event.LoginEvent
import com.pivnoydevelopment.cafeapp.features.auth.ui.login.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sessionManager: SessionManager,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val emailPattern = android.util.Patterns.EMAIL_ADDRESS
    private val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}\$")

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.LoginChanged -> {
                _state.update { it.copy(login = event.login) }
                validate()
            }
            is LoginEvent.PasswordChanged -> {
                _state.update { it.copy(password = event.password) }
                validate()
            }
            is LoginEvent.Submit -> {
                submitLogin()
            }
        }
    }

    private fun validate() {
        val login = _state.value.login
        val password = _state.value.password

        val loginError = if (login.isNotEmpty() && !emailPattern.matcher(login).matches())
            resourceProvider.getString(R.string.invalid_email) else null

        val passwordError = if (password.isNotEmpty() && !passwordPattern.matches(password))
            resourceProvider.getString(R.string.invalid_password) else null

        _state.update {
            it.copy(
                loginError = loginError,
                passwordError = passwordError,
                isButtonEnabled = login.isNotEmpty() &&
                    password.isNotEmpty() &&
                    loginError == null &&
                    passwordError == null
            )
        }
    }

    private fun submitLogin() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = loginUseCase.invoke(
                _state.value.login,
                _state.value.password
            )

            when (result) {
                is NetworkResult.Success -> {
                    sessionManager.saveAuthData(result.data)
                    _state.update {
                        it.copy(
                            isLoading = false,
                            navigateToCoffeeList = true
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
                is NetworkResult.NotFound -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            loginError = resourceProvider.getString(R.string.user_not_found)
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