package com.example.talkiesocial.data.remote

import com.example.talkiesocial.data.remote.model.LoginRequest
import com.example.talkiesocial.data.remote.model.RegisterRequest
import com.example.talkiesocial.domain.model.auth.AuthUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthUser>

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<AuthUser>
}
