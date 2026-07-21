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
fun RegisterRoute(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    BaseScreen<RegisterUiState, AuthEffect>(
        viewModel = viewModel,
        onEffect = { effect ->
            when (effect) {
                is AuthEffect.NavigateToHome -> onRegisterSuccess()
                else -> Unit
            }
        }
    ) { state, padding ->
        RegisterScreen(
            state = state,
            padding = padding,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
            onRegisterClick = viewModel::register,
            onNavigateToLogin = onNavigateToLogin
        )
    }
}

@Composable
fun RegisterScreen(
    state: RegisterUiState,
    padding: PaddingValues,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onNavigateToLogin: () -> Unit
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
                .height(240.dp)
        ) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1614728263952-84ea206f99b6?q=80&w=1000&auto=format&fit=crop",
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

            // Small Logo
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.Center)
                    .neonGlow(
                        color = MaterialTheme.colorScheme.tertiary,
                        borderRadius = 15.dp,
                        blurRadius = 20.dp
                    )
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "t",
                    fontSize = 32.sp,
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
                text = "Join Talkie",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp
                ),
                color = Color.White
            )
            
            Text(
                text = "Create your digital avatar.",
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

            Spacer(modifier = Modifier.height(20.dp))

            TalkieTextField(
                value = state.confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = "CONFIRM PASSWORD",
                placeholder = "••••••••",
                isError = state.confirmPasswordError != null,
                errorString = state.confirmPasswordError?.asString()
            )

            Spacer(modifier = Modifier.height(32.dp))

            TalkieButton(
                text = "Create Account",
                onClick = onRegisterClick,
                containerColor = MaterialTheme.colorScheme.tertiary
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already a member? ",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
                TextButton(
                    onClick = onNavigateToLogin,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Sign In",
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(padding.calculateBottomPadding()))
    }
}
