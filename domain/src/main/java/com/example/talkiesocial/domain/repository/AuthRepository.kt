package com.example.talkiesocial.domain.repository

import com.example.talkiesocial.core.common.result.BaseError
import com.example.talkiesocial.core.common.result.AppResult
import com.example.talkiesocial.domain.model.auth.AuthUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): AppResult<AuthUser, BaseError>
    suspend fun register(email: String, password: String): AppResult<AuthUser, BaseError>
    fun getAuthState(): Flow<AuthUser?>
    suspend fun logout()
}
