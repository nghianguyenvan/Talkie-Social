package com.example.talkiesocial.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.talkiesocial.core.ui.util.neonGlow

data class BottomNavItem(val label: String, val icon: ImageVector, val index: Int)

@Composable
fun TalkieBottomBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp
    ) {
        val items = listOf(
            BottomNavItem("Home", Icons.Default.Home, 0),
            BottomNavItem("Explore", Icons.Default.Explore, 1),
            BottomNavItem("Chat", Icons.AutoMirrored.Filled.Chat, 2),
            BottomNavItem("Profile", Icons.Default.Person, 3)
        )

        items.forEach { item ->
            NavigationBarItem(
                selected = selectedTab == item.index,
                onClick = { onTabSelected(item.index) },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        modifier = if (selectedTab == item.index) Modifier.neonGlow(MaterialTheme.colorScheme.primary, blurRadius = 10.dp) else Modifier
                    )
                },
                label = { Text(item.label, fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
