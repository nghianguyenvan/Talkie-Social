package com.example.talkiesocial.domain.usecase.auth

import com.example.talkiesocial.core.common.result.AppResult
import com.example.talkiesocial.core.common.result.BaseError
import com.example.talkiesocial.domain.model.auth.AuthError
import com.example.talkiesocial.domain.model.auth.AuthUser
import com.example.talkiesocial.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): AppResult<AuthUser, BaseError> {
        if (email.isBlank()) return AppResult.Error(AuthError.InvalidEmail)
        if (password.length < 6) return AppResult.Error(AuthError.InvalidPassword)
        
        return repository.register(email, password)
    }
}
