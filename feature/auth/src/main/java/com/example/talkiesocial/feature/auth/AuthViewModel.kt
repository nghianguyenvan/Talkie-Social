package com.example.talkiesocial.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talkiesocial.core.common.result.Result
import com.example.talkiesocial.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<Result<Unit>>(Result.Success(Unit))
    val loginState = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = Result.Loading
            try {
                loginUseCase(email, password)
                _loginState.value = Result.Success(Unit)
            } catch (e: Exception) {
                _loginState.value = Result.Error(e)
            }
        }
    }
}
