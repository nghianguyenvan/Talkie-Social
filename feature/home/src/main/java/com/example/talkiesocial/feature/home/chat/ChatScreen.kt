package com.example.talkiesocial.feature.home.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.talkiesocial.core.ui.base.BaseScreen
import com.example.talkiesocial.core.ui.components.TalkieBottomBar
import com.example.talkiesocial.core.ui.util.neonGlow

@Composable
fun ChatRoute(
    onTabSelected: (Int) -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    BaseScreen(
        viewModel = viewModel,
        bottomBar = {
            TalkieBottomBar(selectedTab = 2, onTabSelected = onTabSelected)
        }
    ) { state, padding ->
        ChatScreen(state = state, padding = padding)
    }
}

@Composable
fun ChatScreen(
    state: ChatUiState,
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(padding)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Messages",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.conversations) { chat ->
                ChatListItem(chat)
            }
        }
    }
}

@Composable
fun ChatListItem(chat: Conversation) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(52.dp)) {
            AsyncImage(
                model = "https://i.pravatar.cc/150?u=${chat.name}",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape),
                contentScale = ContentScale.Crop
            )
            if (chat.isOnline) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .align(Alignment.BottomEnd)
                        .neonGlow(Color(0xFF00FF94), blurRadius = 8.dp)
                        .background(Color(0xFF00FF94), CircleShape)
                        .border(2.dp, Color.Black, CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(chat.name, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 15.sp)
                Text(chat.time, fontSize = 10.sp, color = Color.Gray)
            }
            Text(
                chat.lastMessage,
                fontSize = 13.sp,
                color = Color.Gray,
                maxLines = 1
            )
        }
    }
}
