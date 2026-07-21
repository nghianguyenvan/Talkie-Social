package com.example.talkiesocial.feature.auth

import androidx.lifecycle.viewModelScope
import com.example.talkiesocial.core.common.base.BaseViewModel
import com.example.talkiesocial.core.common.base.CommonEffect
import com.example.talkiesocial.core.common.result.AppResult
import com.example.talkiesocial.core.common.result.BaseError
import com.example.talkiesocial.core.common.result.DataError
import com.example.talkiesocial.core.ui.util.UiText
import com.example.talkiesocial.domain.model.auth.AuthError
import com.example.talkiesocial.domain.usecase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isRegisterSuccess: Boolean = false,
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
    val confirmPasswordError: UiText? = null
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<RegisterUiState, AuthEffect>() {

    override val initialState = RegisterUiState()

    fun onEmailChange(newValue: String) {
        updateState { it.copy(email = newValue, emailError = null) }
    }

    fun onPasswordChange(newValue: String) {
        updateState { it.copy(password = newValue, passwordError = null) }
    }

    fun onConfirmPasswordChange(newValue: String) {
        updateState { it.copy(confirmPassword = newValue, confirmPasswordError = null) }
    }

    fun register() {
        val email = uiState.value.email
        val password = uiState.value.password
        val confirmPassword = uiState.value.confirmPassword

        if (password != confirmPassword) {
            updateState { it.copy(confirmPasswordError = UiText.DynamicString("Mật khẩu không khớp")) }
            return
        }

        viewModelScope.launch {
            setLoading(true)
            val result = registerUseCase(email, password)
            setLoading(false)

            when (result) {
                is AppResult.Success -> {
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
            is AuthError.UserAlreadyExists -> "Email này đã được sử dụng"
            is AuthError.InvalidEmail -> "Tài khoản không tồn tại hoặc không chính xác"
            is AuthError.InvalidPassword -> "Mật khẩu phải từ 6 ký tự"
            is DataError.Network.NoInternet -> "Không có kết nối mạng"
            else -> "Đăng ký thất bại, vui lòng thử lại"
        }
    }
}
