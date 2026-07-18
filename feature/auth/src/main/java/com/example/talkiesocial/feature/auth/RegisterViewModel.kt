package com.example.talkiesocial.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkiesocial.core.common.result.AppResult
import com.example.talkiesocial.core.common.result.BaseError
import com.example.talkiesocial.core.common.result.DataError
import com.example.talkiesocial.core.ui.util.UiText
import com.example.talkiesocial.domain.model.auth.AuthError
import com.example.talkiesocial.domain.usecase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isRegisterSuccess: Boolean = false,
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
    val confirmPasswordError: UiText? = null
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _errorEvents = MutableSharedFlow<UiText>()
    val errorEvents = _errorEvents.asSharedFlow()

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue, emailError = null) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue, passwordError = null) }
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update { it.copy(confirmPassword = newValue, confirmPasswordError = null) }
    }

    fun register() {
        val email = _uiState.value.email
        val password = _uiState.value.password
        val confirmPassword = _uiState.value.confirmPassword

        if (password != confirmPassword) {
            _uiState.update { it.copy(confirmPasswordError = UiText.DynamicString("Mật khẩu không khớp")) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = registerUseCase(email, password)
            _uiState.update { it.copy(isLoading = false) }

            when (result) {
                is AppResult.Success -> {
                    _uiState.update { it.copy(isRegisterSuccess = true) }
                }
                is AppResult.Error -> {
                    _errorEvents.emit(result.error.toUiText())
                }
            }
        }
    }

    private fun BaseError.toUiText(): UiText {
        return when (this) {
            is AuthError.UserAlreadyExists -> UiText.DynamicString("Email này đã được sử dụng")
            is AuthError.InvalidEmail -> UiText.DynamicString("Email không hợp lệ")
            is AuthError.InvalidPassword -> UiText.DynamicString("Mật khẩu phải từ 6 ký tự")
            is DataError.Network.NoInternet -> UiText.DynamicString("Không có kết nối mạng")
            else -> UiText.DynamicString("Đã có lỗi xảy ra")
        }
    }
}
