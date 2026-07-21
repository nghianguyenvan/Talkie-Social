package com.example.talkiesocial.feature.home.explore

import com.example.talkiesocial.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class AiSoul(
    val id: String,
    val name: String,
    val tag: String,
    val imageUrl: String
)

data class ExploreUiState(
    val trendingSouls: List<AiSoul> = emptyList(),
    val searchQuery: String = ""
)

@HiltViewModel
class ExploreViewModel @Inject constructor() : BaseViewModel<ExploreUiState, Unit>() {
    override val initialState = ExploreUiState(
        trendingSouls = listOf(
            AiSoul("1", "Aria", "MUSICIAN", "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=300"),
            AiSoul("2", "Kael", "PHILOSOPHER", "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=300"),
            AiSoul("3", "Nova", "EXPLORER", "https://images.unsplash.com/photo-1531746020798-e6953c6e8e04?w=300"),
            AiSoul("4", "Zephyr", "HACKER", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=300")
        )
    )

    fun onSearchQueryChange(query: String) {
        updateState { it.copy(searchQuery = query) }
    }
}
