package com.example.talkiesocial.data.repository.auth

import com.example.talkiesocial.domain.model.auth.AuthUser
import com.example.talkiesocial.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    private val _authUser = MutableStateFlow<AuthUser?>(null)

    override suspend fun login(email: String, password: String): AuthUser {
        delay(2000) // Simulate network delay
        if (password == "error") throw Exception("Invalid credentials")
        
        val user = AuthUser(
            id = "1",
            email = email,
            token = "mock_token_123"
        )
        _authUser.value = user
        return user
    }

    override suspend fun register(email: String, password: String): AuthUser {
        delay(2000)
        val user = AuthUser(
            id = "2",
            email = email,
            token = "mock_token_456"
        )
        _authUser.value = user
        return user
    }

    override fun getAuthState(): Flow<AuthUser?> = _authUser.asStateFlow()

    override suspend fun logout() {
        _authUser.value = null
    }
}
