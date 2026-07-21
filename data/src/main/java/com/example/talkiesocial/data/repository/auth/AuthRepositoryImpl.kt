package com.example.talkiesocial.data.repository.auth

import com.example.talkiesocial.core.common.result.AppResult
import com.example.talkiesocial.core.common.result.BaseError
import com.example.talkiesocial.domain.model.auth.AuthError
import com.example.talkiesocial.domain.model.auth.AuthUser
import com.example.talkiesocial.domain.repository.AuthRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val supabaseAuth: Auth
) : AuthRepository {

    // Tự động chuyển đổi trạng thái Session của Supabase sang AuthUser của app
    override fun getAuthState(): Flow<AuthUser?> {
        return supabaseAuth.sessionStatus.map { status ->
            when (status) {
                is SessionStatus.Authenticated -> {
                    val user = status.session.user
                    if (user != null) AuthUser(user.id, user.email ?: "", "") else null
                }
                else -> null
            }
        }
    }

    override suspend fun login(email: String, password: String): AppResult<AuthUser, BaseError> {
        return try {
            supabaseAuth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            val user = supabaseAuth.currentUserOrNull()
            if (user != null) {
                AppResult.Success(AuthUser(user.id, user.email ?: "", ""))
            } else {
                AppResult.Error(AuthError.UserNotFound)
            }
        } catch (e: Exception) {
            // Phân loại lỗi dựa trên nội dung hoặc type của exception từ Supabase
            val error = when {
                e.message?.contains("Invalid login credentials", ignoreCase = true) == true -> AuthError.WrongPassword
                e.message?.contains("Email not confirmed", ignoreCase = true) == true -> AuthError.InvalidEmail
                else -> AuthError.UserNotFound // Fallback
            }
            AppResult.Error(error)
        }
    }

    override suspend fun register(email: String, password: String): AppResult<AuthUser, BaseError> {
        return try {
            supabaseAuth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            val user = supabaseAuth.currentUserOrNull()
            if (user != null) {
                AppResult.Success(AuthUser(user.id, user.email ?: "", ""))
            } else {
                AppResult.Error(AuthError.InvalidEmail)
            }
        } catch (e: Exception) {
            val error = when {
                e.message?.contains("already registered", ignoreCase = true) == true -> AuthError.UserAlreadyExists
                e.message?.contains("valid email", ignoreCase = true) == true -> AuthError.InvalidEmail
                else -> AuthError.Unknown // Fallback
            }
            AppResult.Error(error)
        }
    }

    override suspend fun logout() {
        supabaseAuth.signOut()
    }
}
