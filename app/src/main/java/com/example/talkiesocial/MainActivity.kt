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
import com.example.talkiesocial.core.common.navigation.ChatRoute
import com.example.talkiesocial.core.common.navigation.ExploreRoute
import com.example.talkiesocial.core.common.navigation.HomeRoute
import com.example.talkiesocial.core.common.navigation.LoginRoute
import com.example.talkiesocial.core.common.navigation.ProfileRoute
import com.example.talkiesocial.core.common.navigation.RegisterRoute
import com.example.talkiesocial.core.ui.theme.TalkieSocialTheme
import com.example.talkiesocial.feature.auth.LoginRoute
import com.example.talkiesocial.feature.auth.RegisterRoute
import com.example.talkiesocial.feature.home.HomeRoute
import com.example.talkiesocial.feature.home.chat.ChatRoute
import com.example.talkiesocial.feature.home.explore.ExploreRoute
import com.example.talkiesocial.feature.home.profile.ProfileRoute
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
                            HomeRoute(
                                onNavigateToChat = { chatId ->
                                    // navController.navigate(ChatDetailRoute(chatId))
                                },
                                onTabSelected = { index ->
                                    navigateByTab(navController, index)
                                }
                            )
                        }
                        composable<ExploreRoute> {
                            ExploreRoute(
                                onTabSelected = { index ->
                                    navigateByTab(navController, index)
                                }
                            )
                        }
                        composable<ChatRoute> {
                            ChatRoute(
                                onTabSelected = { index ->
                                    navigateByTab(navController, index)
                                }
                            )
                        }
                        composable<ProfileRoute> {
                            ProfileRoute(
                                onTabSelected = { index ->
                                    navigateByTab(navController, index)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun navigateByTab(navController: androidx.navigation.NavController, index: Int) {
        val route = when (index) {
            0 -> HomeRoute
            1 -> ExploreRoute
            2 -> ChatRoute
            3 -> ProfileRoute
            else -> HomeRoute
        }
        navController.navigate(route) {
            popUpTo(HomeRoute) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }
}
