package com.example.talkiesocial.feature.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.talkiesocial.core.common.result.Result
import com.example.talkiesocial.core.ui.components.TalkieButton
import com.example.talkiesocial.core.ui.components.TalkieTextField
import com.example.talkiesocial.core.ui.theme.NeonPurple

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState is Result.Success && email.isNotBlank()) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
            value = email,
            onValueChange = { email = it },
            label = "Email",
            enabled = loginState !is Result.Loading
        )

        Spacer(modifier = Modifier.height(16.dp))

        TalkieTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            enabled = loginState !is Result.Loading
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (loginState is Result.Error) {
            Text(
                text = (loginState as Result.Error).exception?.message ?: "Unknown error",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (loginState is Result.Loading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        } else {
            TalkieButton(
                text = "Login",
                onClick = { viewModel.login(email, password) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = onNavigateToRegister,
            enabled = loginState !is Result.Loading
        ) {
            Text(text = "Don't have an account? Register")
        }
    }
}
