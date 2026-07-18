package com.example.talkiesocial.domain.usecase.auth

import com.example.talkiesocial.core.common.result.AppResult
import com.example.talkiesocial.core.common.result.BaseError
import com.example.talkiesocial.domain.model.auth.AuthError
import com.example.talkiesocial.domain.model.auth.AuthUser
import com.example.talkiesocial.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): AppResult<AuthUser, BaseError> {
        if (email.isBlank() || password.isBlank()) {
            return AppResult.Error(AuthError.InvalidEmail)
        }
        return repository.login(email, password)
    }
}
