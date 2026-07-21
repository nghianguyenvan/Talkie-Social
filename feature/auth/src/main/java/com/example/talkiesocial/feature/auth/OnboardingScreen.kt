package com.example.talkiesocial.feature.auth

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talkiesocial.core.ui.components.TalkieButton

data class OnboardingPage(
    val icon: String,
    val title: String,
    val description: String
)

val pages = listOf(
    OnboardingPage("🧠", "AI Soul", "Meet AI characters that feel alive and remember your pulses."),
    OnboardingPage("⚡", "Electric Pulse", "Connect with a community that vibrates with creative energy."),
    OnboardingPage("🌐", "Digital World", "Build your own universe and share it with the world.")
)

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    var currentPage by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        AnimatedContent(
            targetState = pages[currentPage],
            transitionSpec = {
                fadeIn() + slideInVertically { it } togetherWith fadeOut() + slideOutVertically { -it }
            },
            label = "OnboardingContent"
        ) { page ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = page.icon,
                    fontSize = 80.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                Text(
                    text = page.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    ),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = page.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            pages.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .size(height = 8.dp, width = if (index == currentPage) 24.dp else 8.dp)
                        .background(
                            color = if (index == currentPage) MaterialTheme.colorScheme.primary else Color(0xFF333333),
                            shape = CircleShape
                        )
                )
            }
        }

        TalkieButton(
            text = if (currentPage == pages.size - 1) "Get Started" else "Next",
            onClick = {
                if (currentPage < pages.size - 1) {
                    currentPage++
                } else {
                    onFinish()
                }
            }
        )
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}
