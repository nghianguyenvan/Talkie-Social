package com.example.talkiesocial.core.common.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object LoginRoute

@Serializable
object RegisterRoute

@Serializable
data class ChatDetailRoute(val chatId: String)
