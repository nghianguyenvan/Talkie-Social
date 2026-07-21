package com.example.talkiesocial.feature.home.chat

import com.example.talkiesocial.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class Conversation(
    val id: String,
    val name: String,
    val lastMessage: String,
    val time: String,
    val isOnline: Boolean
)

data class ChatUiState(
    val conversations: List<Conversation> = emptyList()
)

@HiltViewModel
class ChatViewModel @Inject constructor() : BaseViewModel<ChatUiState, Unit>() {
    override val initialState = ChatUiState(
        conversations = listOf(
            Conversation("1", "Aria", "Did you hear that pulse?", "2m", true),
            Conversation("2", "Nexus_AI", "Data stream ready.", "15m", true),
            Conversation("3", "System", "Welcome to Talkie!", "2h", false),
            Conversation("4", "Luna", "Let's sync tonight.", "Yesterday", false)
        )
    )
}
