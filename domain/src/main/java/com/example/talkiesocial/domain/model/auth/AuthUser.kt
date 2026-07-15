package com.example.talkiesocial.domain.model.auth

data class AuthUser(
    val id: String,
    val email: String,
    val token: String
)
