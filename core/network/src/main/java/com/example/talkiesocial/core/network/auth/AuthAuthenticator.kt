package com.example.talkiesocial.core.network.auth

import com.example.talkiesocial.core.common.auth.AuthManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val authManager: AuthManager
    // private val authApi: Provider<AuthApi> // Sử dụng Provider để tránh circular dependency
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking { authManager.refreshToken.first() }
        
        if (refreshToken == null) return null

        // Ở đây bạn sẽ gọi API Refresh Token
        // Giả sử logic:
        // val newTokens = authApi.get().refreshToken(refreshToken)
        // if (newTokens.isSuccessful) {
        //     runBlocking { authManager.saveTokens(newTokens.access, newTokens.refresh) }
        //     return response.request.newBuilder()
        //         .header("Authorization", "Bearer ${newTokens.access}")
        //         .build()
        // }
        
        return null
    }
}
