package com.example.talkiesocial.domain.repository

import com.example.talkiesocial.domain.model.auth.AuthUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthUser
    suspend fun register(email: String, password: String): AuthUser
    fun getAuthState(): Flow<AuthUser?>
    suspend fun logout()
}
