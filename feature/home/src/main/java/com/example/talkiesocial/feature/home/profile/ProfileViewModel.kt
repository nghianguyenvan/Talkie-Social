package com.example.talkiesocial.feature.home.profile

import com.example.talkiesocial.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class UserProfile(
    val username: String,
    val bio: String,
    val pulsesCount: String,
    val followersCount: String,
    val soulsCount: String,
    val avatarUrl: String,
    val coverUrl: String
)

data class ProfileUiState(
    val profile: UserProfile? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor() : BaseViewModel<ProfileUiState, Unit>() {
    override val initialState = ProfileUiState(
        profile = UserProfile(
            username = "Cyber_Nghia",
            bio = "Architect of the Metaverse",
            pulsesCount = "1.2k",
            followersCount = "482",
            soulsCount = "95",
            avatarUrl = "https://i.pravatar.cc/150?u=me",
            coverUrl = "https://images.unsplash.com/photo-1614850523296-d8c1af93d400?w=600"
        )
    )
}
