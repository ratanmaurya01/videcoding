package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.VibeViewModel
import com.example.ui.components.EmptyState
import com.example.ui.components.ProfileCard
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
    viewModel: VibeViewModel,
    onNavigateToBlogDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val username by viewModel.username.collectAsState()
    val email by viewModel.email.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val pushEnabled by viewModel.pushNotificationsEnabled.collectAsState()
    val language by viewModel.selectedLanguage.collectAsState()

    val bookmarkedBlogs by viewModel.bookmarkedBlogs.collectAsState()

    var activeDialog by remember { mutableStateOf<String?>(null) } // "about", "privacy", "terms", "settings"

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Hero Profile Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .background(
                                brush = Brush.linearGradient(colors = listOf(VibePrimary, VibeSecondary)),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Avatar",
                            tint = VibeOnPrimary,
                            modifier = Modifier.size(52.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = username,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = VibeMutedText,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // Quick Toggle Theme
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(VibeSurfaceVariant, RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "Premium Dark Mode",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = if (isDarkMode) "Slate Dark Enabled" else "Light Theme Enabled",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = VibeMutedText
                                )
                            }
                        }
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { viewModel.toggleDarkMode() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = VibeOnPrimary,
                                checkedTrackColor = VibePrimary,
                                uncheckedThumbColor = VibeMutedText,
                                uncheckedTrackColor = VibeSurfaceVariant
                            )
                        )
                    }
                }
            }

            // Saved Articles & Bookmarks Section Header
            item {
                Text(
                    text = "Saved Lessons & Bookmarks (${bookmarkedBlogs.size})",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 8.dp)
                )
            }

            // Bookmarked list inside Column
            if (bookmarkedBlogs.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.BookmarkBorder,
                                contentDescription = null,
                                tint = VibeMutedText,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "No bookmarked lessons yet",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = VibeMutedText,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Bookmark articles in the Academy tab to easily access them here Offline.",
                                style = MaterialTheme.typography.bodySmall,
                                color = VibeMutedText,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            } else {
                items(bookmarkedBlogs, key = { it.id }) { blog ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .clickable { onNavigateToBlogDetail(blog.id) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(VibeSurfaceVariant),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Book,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = blog.title,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "Category: ${blog.category} • ${blog.readTime}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = VibeMutedText
                                )
                            }
                            IconButton(
                                onClick = { viewModel.toggleBookmark(blog.id) }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Bookmark,
                                    contentDescription = "Remove bookmark",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }

            // Preferences list
            item {
                Text(
                    text = "Preferences & System Settings",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 8.dp)
                )
            }

            item {
                ProfileCard(
                    title = "System Toggles",
                    value = "Notifications, Language & Alerts",
                    icon = Icons.Default.Settings,
                    onClick = { activeDialog = "settings" }
                )
            }

            item {
                ProfileCard(
                    title = "About VibeTrading",
                    value = "V1.0.4 Premium Educational Release",
                    icon = Icons.Default.Info,
                    onClick = { activeDialog = "about" }
                )
            }

            item {
                ProfileCard(
                    title = "Privacy Policy",
                    value = "Read our data policy & licenses",
                    icon = Icons.Default.Security,
                    onClick = { activeDialog = "privacy" }
                )
            }

            item {
                ProfileCard(
                    title = "Terms of Service",
                    value = "Our legal agreement & guidelines",
                    icon = Icons.Default.Gavel,
                    onClick = { activeDialog = "terms" }
                )
            }

            // Logout Action
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.logout()
                        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VibeNegativeRed.copy(alpha = 0.15f),
                        contentColor = VibeNegativeRed
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Logout",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Logout from Account", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                }
            }
        }

        // About Dialog
        if (activeDialog == "about") {
            LegalDialog(
                title = "About VibeTrading",
                content = "VibeTrading is a modern, responsive, premium stock market learning, blogging, and analytics tracker. This applet features a beautiful M3 Dark Theme, real-time reactive watchlist additions, in-depth academy lectures, and simulated index tracking. Designed from the ground up to prevent dead-end UI flows.",
                onDismiss = { activeDialog = null }
            )
        }

        // Privacy Dialog
        if (activeDialog == "privacy") {
            LegalDialog(
                title = "Privacy Policy",
                content = "Your privacy is fully protected under VibeTrading guidelines. All watchlist symbols, bookmark histories, preferences, and session logs are retained completely locally in an offline Room SQLite database structure. No external telemetry or trackers are active.",
                onDismiss = { activeDialog = null }
            )
        }

        // Terms Dialog
        if (activeDialog == "terms") {
            LegalDialog(
                title = "Terms of Service",
                content = "By utilizing the VibeTrading app, you acknowledge that all pricing, tickers, cryptocurrency values, and market notifications displayed within the application are simulated dummy datasets. These charts, blogs, and articles are strictly for informational and educational purposes only and do not constitute actual financial advice.",
                onDismiss = { activeDialog = null }
            )
        }

        // Settings Dialog
        if (activeDialog == "settings") {
            SettingsSheetDialog(
                pushEnabled = pushEnabled,
                onTogglePush = { viewModel.toggleNotifications() },
                selectedLanguage = language,
                onSelectLanguage = { viewModel.setLanguage(it) },
                onDismiss = { activeDialog = null }
            )
        }
    }
}

@Composable
fun LegalDialog(
    title: String,
    content: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        },
        text = {
            Text(text = content, style = MaterialTheme.typography.bodyMedium, color = VibeMutedText, lineHeight = 22.sp)
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Got It", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
        },
        shape = RoundedCornerShape(20.dp),
        containerColor = MaterialTheme.colorScheme.surface
    )
}

@Composable
fun SettingsSheetDialog(
    pushEnabled: Boolean,
    onTogglePush: () -> Unit,
    selectedLanguage: String,
    onSelectLanguage: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val languages = listOf("English", "Hindi", "Spanish", "German")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Preferences & Settings", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Push Notifications", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text("Receive updates & pricing alerts", style = MaterialTheme.typography.bodySmall, color = VibeMutedText)
                    }
                    Switch(
                        checked = pushEnabled,
                        onCheckedChange = { onTogglePush() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = VibeOnPrimary,
                            checkedTrackColor = VibePrimary
                        )
                    )
                }

                Divider(color = VibeSurfaceVariant, modifier = Modifier.padding(vertical = 8.dp))

                Text(
                    text = "Application Language",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                languages.forEach { lang ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onSelectLanguage(lang) }
                            .padding(vertical = 10.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = lang, color = MaterialTheme.colorScheme.onSurface)
                        RadioButton(
                            selected = selectedLanguage == lang,
                            onClick = { onSelectLanguage(lang) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = VibePrimary
                            )
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
        },
        shape = RoundedCornerShape(20.dp),
        containerColor = MaterialTheme.colorScheme.surface
    )
}
