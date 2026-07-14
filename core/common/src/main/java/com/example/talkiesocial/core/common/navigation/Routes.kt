package com.example.talkiesocial.core.common.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
data class ChatDetailRoute(val chatId: String)
