package com.example.talkiesocial.domain.usecase.auth

import com.example.talkiesocial.domain.model.auth.AuthUser
import com.example.talkiesocial.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): AuthUser {
        // Here you can add business logic, like validation
        if (email.isBlank() || password.isBlank()) {
            throw IllegalArgumentException("Email and password cannot be empty")
        }
        return repository.login(email, password)
    }
}
