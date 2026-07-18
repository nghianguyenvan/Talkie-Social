package com.example.talkiesocial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.talkiesocial.core.common.navigation.HomeRoute
import com.example.talkiesocial.core.common.navigation.LoginRoute
import com.example.talkiesocial.core.common.navigation.RegisterRoute
import com.example.talkiesocial.core.ui.theme.TalkieSocialTheme
import com.example.talkiesocial.feature.auth.LoginRoute
import com.example.talkiesocial.feature.auth.RegisterRoute
import com.example.talkiesocial.feature.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TalkieSocialTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = LoginRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<LoginRoute> {
                            LoginRoute(
                                onLoginSuccess = {
                                    navController.navigate(HomeRoute) {
                                        popUpTo(LoginRoute) { inclusive = true }
                                    }
                                },
                                onNavigateToRegister = {
                                    navController.navigate(RegisterRoute)
                                }
                            )
                        }
                        composable<RegisterRoute> {
                            RegisterRoute(
                                onRegisterSuccess = {
                                    navController.navigate(HomeRoute) {
                                        popUpTo(LoginRoute) { inclusive = true }
                                    }
                                },
                                onNavigateToLogin = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable<HomeRoute> {
                            HomeScreen(
                                onChatClick = { chatId ->
                                    // navController.navigate(ChatDetailRoute(chatId))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
