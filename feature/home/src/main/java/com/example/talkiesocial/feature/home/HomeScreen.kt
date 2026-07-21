package com.example.talkiesocial.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.talkiesocial.core.ui.base.BaseScreen
import com.example.talkiesocial.core.ui.components.PulseCard
import com.example.talkiesocial.core.ui.components.PulseType
import com.example.talkiesocial.core.ui.components.TalkieBottomBar
import com.example.talkiesocial.core.ui.util.neonGlow

data class PulsePost(
    val id: String,
    val username: String,
    val avatarUrl: String,
    val content: String,
    val type: PulseType,
    val imageUrl: String? = null,
    val resonateFrom: String? = null,
    val duration: String = "0:42",
    val timeAgo: String,
    val likes: String,
    val comments: String,
    val shares: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoute(
    onNavigateToChat: (String) -> Unit,
    onTabSelected: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    BaseScreen(
        viewModel = viewModel,
        onEffect = { effect ->
            when (effect) {
                is HomeEffect.NavigateToChat -> onNavigateToChat(effect.chatId)
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "TALKIE",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            TalkieBottomBar(selectedTab = 0, onTabSelected = onTabSelected)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Post Pulse */ },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier
                    .neonGlow(MaterialTheme.colorScheme.primary, borderRadius = 30.dp)
            ) {
                Icon(Icons.Default.Mic, contentDescription = "Post Pulse", tint = Color.Black)
            }
        }
    ) { state, padding ->
        HomeScreen(
            state = state,
            padding = padding,
            onChatClick = viewModel::onChatClick
        )
    }
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    padding: PaddingValues,
    onChatClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(padding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text(
                "Discover Pulses",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(state.pulses) { pulse ->
            PulseCard(
                type = pulse.type,
                username = pulse.username,
                avatarUrl = pulse.avatarUrl,
                content = pulse.content,
                timeAgo = pulse.timeAgo,
                imageUrl = pulse.imageUrl,
                resonateFrom = pulse.resonateFrom,
                likes = pulse.likes,
                comments = pulse.comments,
                shares = pulse.shares
            )
        }
        
        item { Spacer(modifier = Modifier.height(80.dp)) }
    }
}
