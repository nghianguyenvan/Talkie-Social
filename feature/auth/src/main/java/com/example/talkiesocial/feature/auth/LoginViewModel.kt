package com.example.talkiesocial.feature.auth

import androidx.lifecycle.viewModelScope
import com.example.talkiesocial.core.common.base.BaseViewModel
import com.example.talkiesocial.core.common.base.CommonEffect
import com.example.talkiesocial.core.common.result.AppResult
import com.example.talkiesocial.core.common.result.BaseError
import com.example.talkiesocial.core.common.result.DataError
import com.example.talkiesocial.core.ui.util.UiText
import com.example.talkiesocial.domain.model.auth.AuthError
import com.example.talkiesocial.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoginSuccess: Boolean = false,
    val emailError: UiText? = null,
    val passwordError: UiText? = null
)

sealed interface AuthEffect {
    data class ShowError(override val message: String) : AuthEffect, CommonEffect.ShowSnackbar
    data object NavigateToHome : AuthEffect
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginUiState, AuthEffect>() {

    override val initialState = LoginUiState()

    fun onEmailChange(newValue: String) {
        updateState { it.copy(email = newValue, emailError = null) }
    }

    fun onPasswordChange(newValue: String) {
        updateState { it.copy(password = newValue, passwordError = null) }
    }

    fun login() {
        val email = uiState.value.email
        val password = uiState.value.password

        viewModelScope.launch {
            setLoading(true)
            
            val result = loginUseCase(email, password)
            
            setLoading(false)
            
            when (result) {
                is AppResult.Success -> {
                    updateState { it.copy(isLoginSuccess = true) }
                    sendEffect(AuthEffect.NavigateToHome)
                }
                is AppResult.Error -> {
                    sendEffect(AuthEffect.ShowError(result.error.toMessage()))
                }
            }
        }
    }

    private fun BaseError.toMessage(): String {
        return when (this) {
            is AuthError.WrongPassword -> "Email hoặc mật khẩu không đúng"
            is AuthError.UserNotFound -> "Không tìm thấy người dùng"
            is AuthError.InvalidEmail -> "Email không hợp lệ"
            is DataError.Network.NoInternet -> "Không có kết nối mạng"
            is DataError.Network.Timeout -> "Kết nối quá hạn"
            else -> "Đã có lỗi xảy ra"
        }
    }
}
