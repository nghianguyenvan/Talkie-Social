package com.example.talkiesocial.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkErrorResponse(
    val code: String? = null,
    val message: String? = null
)
