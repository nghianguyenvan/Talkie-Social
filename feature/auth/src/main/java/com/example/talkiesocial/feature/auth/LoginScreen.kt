package com.example.talkiesocial.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.talkiesocial.core.ui.components.TalkieButton
import com.example.talkiesocial.core.ui.components.TalkieTextField
import com.example.talkiesocial.core.ui.util.neonGlow

@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var showOnboarding by remember { mutableStateOf(true) }

    if (showOnboarding) {
        OnboardingScreen(onFinish = { showOnboarding = false })
    } else {
        BaseScreen(
            viewModel = viewModel,
            onEffect = { effect ->
                when (effect) {
                    is AuthEffect.NavigateToHome -> onLoginSuccess()
                    else -> Unit
                }
            }
        ) { state, padding ->
            LoginScreen(
                state = state,
                padding = padding,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,
                onLoginClick = viewModel::login,
                onNavigateToRegister = onNavigateToRegister
            )
        }
    }
}

@Composable
fun LoginScreen(
    state: LoginUiState,
    padding: PaddingValues,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        // Illustration Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            AsyncImage(
                model = "https://www.clker.com/cliparts/b/f/j/P/w/j/purple-flame-logo-2-hi.png",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
                contentScale = ContentScale.Crop,
                alpha = 0.5f
            )
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.7f to MaterialTheme.colorScheme.background,
                            1f to MaterialTheme.colorScheme.background
                        )
                    )
            )

            // Logo
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center)
                    .neonGlow(
                        color = MaterialTheme.colorScheme.tertiary,
                        borderRadius = 20.dp,
                        blurRadius = 30.dp
                    )
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "T",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp
                ),
                color = Color.White
            )
            
            Text(
                text = "Enter your pulse to connect.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
            )

            TalkieTextField(
                value = state.email,
                onValueChange = onEmailChange,
                label = "EMAIL ADDRESS",
                placeholder = "name@pulse.com",
                isError = state.emailError != null
            )

            Spacer(modifier = Modifier.height(20.dp))

            TalkieTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                label = "PASSWORD",
                placeholder = "••••••••",
                isError = state.passwordError != null
            )

            Spacer(modifier = Modifier.height(32.dp))

            TalkieButton(
                text = "Sign In",
                onClick = onLoginClick
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = "Don't have an account? ",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
                TextButton(
                    onClick = onNavigateToRegister,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Join the Pulse",
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        
        // Add extra spacer to ensure content is above navigation bar if needed
        Spacer(modifier = Modifier.height(padding.calculateBottomPadding()))
    }
}
