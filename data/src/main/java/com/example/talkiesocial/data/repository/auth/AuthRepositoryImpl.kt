package com.example.talkiesocial.data.repository.auth

import com.example.talkiesocial.core.common.result.AppResult
import com.example.talkiesocial.core.common.result.BaseError
import com.example.talkiesocial.core.network.util.safeApiCall
import com.example.talkiesocial.data.remote.AuthApiService
import com.example.talkiesocial.data.remote.model.LoginRequest
import com.example.talkiesocial.domain.model.auth.AuthError
import com.example.talkiesocial.domain.model.auth.AuthUser
import com.example.talkiesocial.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(private val authApiService: AuthApiService) : AuthRepository {

    private val _authUser = MutableStateFlow<AuthUser?>(null)

    override suspend fun login(email: String, password: String): AppResult<AuthUser, BaseError> {
        return safeApiCall(
            execute = {
                authApiService.login(LoginRequest(email= email, password= password))
//
            },
            mapBusinessError = { code, _ ->
                when (code) {
                    "INVALID_CREDENTIALS" -> AuthError.WrongPassword
                    "USER_NOT_FOUND" -> AuthError.UserNotFound
                    else -> null
                }
            }
        ).also { result ->
            if (result is AppResult.Success) {
                _authUser.value = result.data
            }
        }
    }

    override suspend fun register(email: String, password: String): AppResult<AuthUser, BaseError> {
        return safeApiCall<AuthUser, AuthError>(
            execute = {
                Response.success(AuthUser("2", email, "token"))
            }
        ).also { result ->
            if (result is AppResult.Success) {
                _authUser.value = result.data
            }
        }
    }

    override fun getAuthState(): Flow<AuthUser?> = _authUser.asStateFlow()

    override suspend fun logout() {
        _authUser.value = null
    }
}
