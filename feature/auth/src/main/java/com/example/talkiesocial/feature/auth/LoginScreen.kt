package com.example.talkiesocial.feature.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.talkiesocial.core.ui.base.BaseScreen
import com.example.talkiesocial.core.ui.components.TalkieButton
import com.example.talkiesocial.core.ui.components.TalkieTextField

@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
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

@Composable
fun LoginScreen(
    state: LoginUiState,
    padding: PaddingValues,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Talkie Social",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.tertiary
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Connect with your voice",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))

        TalkieTextField(
            value = state.email,
            onValueChange = onEmailChange,
            label = "Email",
            isError = state.emailError != null
        )

        Spacer(modifier = Modifier.height(16.dp))

        TalkieTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            label = "Password",
            isError = state.passwordError != null
        )

        Spacer(modifier = Modifier.height(32.dp))

        TalkieButton(
            text = "Login",
            onClick = onLoginClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = onNavigateToRegister,
        ) {
            Text(text = "Don't have an account? Register")
        }
    }
}
