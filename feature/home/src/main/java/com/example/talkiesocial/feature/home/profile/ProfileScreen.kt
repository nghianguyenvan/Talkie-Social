package com.example.talkiesocial.feature.home.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
fun ProfileRoute(
    onTabSelected: (Int) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    BaseScreen(
        viewModel = viewModel,
        bottomBar = {
            TalkieBottomBar(selectedTab = 3, onTabSelected = onTabSelected)
        }
    ) { state, padding ->
        ProfileScreen(state = state, padding = padding)
    }
}

@Composable
fun ProfileScreen(
    state: ProfileUiState,
    padding: PaddingValues
) {
    val profile = state.profile ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = padding.calculateBottomPadding())
    ) {
        // Header with Cover & Avatar
        Box(modifier = Modifier.height(280.dp)) {
            AsyncImage(
                model = profile.coverUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
                contentScale = ContentScale.Crop,
                alpha = 0.5f
            )
            
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.BottomStart)
                    .offset(x = 32.dp, y = (-20).dp)
                    .neonGlow(MaterialTheme.colorScheme.primary, borderRadius = 30.dp, blurRadius = 20.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(30.dp))
                    .border(4.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(30.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = profile.avatarUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(30.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Column(modifier = Modifier.padding(horizontal = 32.dp)) {
            Text(
                text = profile.username,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Text(
                text = profile.bio,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(label = "Pulses", value = profile.pulsesCount)
                StatItem(label = "Followers", value = profile.followersCount)
                StatItem(label = "Souls", value = profile.soulsCount)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.05f))
            ) {
                Text("EDIT PROFILE", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(9) { i ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.05f))
                    ) {
                        AsyncImage(
                            model = "https://picsum.photos/200/200?random=$i",
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            alpha = 0.6f
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column {
        Text(text = value, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
        Text(text = label.uppercase(), color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Black)
    }
}
