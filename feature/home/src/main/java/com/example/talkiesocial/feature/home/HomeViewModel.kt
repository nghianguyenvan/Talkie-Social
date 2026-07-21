package com.example.talkiesocial.feature.home

import com.example.talkiesocial.core.common.base.BaseViewModel
import com.example.talkiesocial.core.ui.components.PulseType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class HomeUiState(
    val pulses: List<PulsePost> = emptyList(),
    val isLoading: Boolean = false
)

sealed interface HomeEffect {
    data class NavigateToChat(val chatId: String) : HomeEffect
}

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<HomeUiState, HomeEffect>() {
    
    override val initialState = HomeUiState(
        pulses = listOf(
            PulsePost(
                id = "1", 
                username = "nexus_ai", 
                avatarUrl = "https://i.pravatar.cc/150?u=1", 
                content = "The digital rain is falling in the Metaverse. Can you hear the frequency of 432Hz?",
                type = PulseType.AUDIO,
                timeAgo = "2m ago",
                likes = "1.2k",
                comments = "84",
                shares = "210"
            ),
            PulsePost(
                id = "2", 
                username = "luna_cyber", 
                avatarUrl = "https://i.pravatar.cc/150?u=2", 
                content = "Just synced my neural link with this view. Absolute magic. ✨",
                type = PulseType.IMAGE,
                imageUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?q=80&w=600",
                timeAgo = "15m ago",
                likes = "850",
                comments = "42",
                shares = "15"
            ),
            PulsePost(
                id = "3", 
                username = "zephyr", 
                avatarUrl = "https://i.pravatar.cc/150?u=3", 
                content = "This is exactly what I was talking about! Decoding the soul...",
                type = PulseType.TEXT,
                resonateFrom = "master_pulse",
                timeAgo = "1h ago",
                likes = "320",
                comments = "12",
                shares = "5"
            )
        )
    )

    fun onChatClick(chatId: String) {
        sendEffect(HomeEffect.NavigateToChat(chatId))
    }
}
