package com.example.ui.navigation

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.VibeViewModel
import com.example.ui.screens.*
import com.example.ui.theme.*

enum class VibeTab(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    HOME("Home", Icons.Filled.Home, Icons.Outlined.Home),
    MARKETS("Markets", Icons.Filled.ShowChart, Icons.Outlined.ShowChart),
    BLOG("Academy", Icons.Filled.Book, Icons.Outlined.Book),
    WATCHLIST("Watchlist", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
    PROFILE("Profile", Icons.Filled.Person, Icons.Outlined.Person)
}

@Composable
fun VibeNavigationController(
    viewModel: VibeViewModel,
    modifier: Modifier = Modifier
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    var currentTab by remember { mutableStateOf(VibeTab.HOME) }
    var activeBlogIdDetail by remember { mutableStateOf<Int?>(null) }
    var inNotificationsScreen by remember { mutableStateOf(false) }

    if (!isLoggedIn) {
        AuthFlowScreen(viewModel = viewModel)
    } else {
        Scaffold(
            bottomBar = {
                if (activeBlogIdDetail == null && !inNotificationsScreen) {
                    Box(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp, top = 4.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(24.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                            shadowElevation = 8.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            NavigationBar(
                                containerColor = Color.Transparent,
                                tonalElevation = 0.dp,
                                modifier = Modifier.height(72.dp)
                            ) {
                                VibeTab.values().forEach { tab ->
                                    val isSelected = currentTab == tab
                                    NavigationBarItem(
                                        selected = isSelected,
                                        onClick = { currentTab = tab },
                                        icon = {
                                            Icon(
                                                imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                                contentDescription = tab.title,
                                                modifier = Modifier.size(22.dp)
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = tab.title,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                            )
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = VibeOnPrimary,
                                            selectedTextColor = MaterialTheme.colorScheme.primary,
                                            indicatorColor = MaterialTheme.colorScheme.primary,
                                            unselectedIconColor = VibeMutedText,
                                            unselectedTextColor = VibeMutedText
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Render Active Tab Content
                Crossfade(targetState = currentTab, label = "tab_fade") { tab ->
                    when (tab) {
                        VibeTab.HOME -> HomeScreen(
                            viewModel = viewModel,
                            onNavigateToBlogDetail = { activeBlogIdDetail = it },
                            onNavigateToNotifications = { inNotificationsScreen = true },
                            onNavigateToMarkets = { currentTab = VibeTab.MARKETS }
                        )
                        VibeTab.MARKETS -> MarketsScreen(viewModel = viewModel)
                        VibeTab.BLOG -> BlogFeedScreen(
                            viewModel = viewModel,
                            onNavigateToBlogDetail = { activeBlogIdDetail = it }
                        )
                        VibeTab.WATCHLIST -> WatchlistScreen(
                            viewModel = viewModel,
                            onNavigateToMarkets = { currentTab = VibeTab.MARKETS }
                        )
                        VibeTab.PROFILE -> ProfileScreen(
                            viewModel = viewModel,
                            onNavigateToBlogDetail = { activeBlogIdDetail = it }
                        )
                    }
                }

                // Sub-screens overlay (Blog Detail & Notifications) with robust animations
                AnimatedVisibility(
                    visible = activeBlogIdDetail != null,
                    enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                    exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
                ) {
                    activeBlogIdDetail?.let { id ->
                        BlogDetailScreen(
                            blogId = id,
                            viewModel = viewModel,
                            onBack = { activeBlogIdDetail = null },
                            onNavigateToBlogDetail = { activeBlogIdDetail = it }
                        )
                    }
                }

                AnimatedVisibility(
                    visible = inNotificationsScreen,
                    enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                    exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
                ) {
                    NotificationScreen(
                        viewModel = viewModel,
                        onBack = { inNotificationsScreen = false }
                    )
                }
            }
        }
    }
}
