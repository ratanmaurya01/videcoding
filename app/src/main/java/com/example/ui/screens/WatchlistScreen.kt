package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.VibeViewModel
import com.example.data.model.Stock
import com.example.ui.components.EmptyState
import com.example.ui.components.MarketCard
import com.example.ui.components.SearchBar
import com.example.ui.theme.*

@Composable
fun WatchlistScreen(
    viewModel: VibeViewModel,
    onNavigateToMarkets: () -> Unit,
    modifier: Modifier = Modifier
) {
    val watchlistStocks by viewModel.watchlistStocks.collectAsState()
    val priceAlerts by viewModel.priceAlerts.collectAsState()
    
    var watchlistSearchQuery by remember { mutableStateOf("") }
    var activeTab by remember { mutableStateOf(0) } // 0: Watchlist, 1: Price Alerts
    
    var selectedDetailedStock by remember { mutableStateOf<Stock?>(null) }
    var showSetAlertForStock by remember { mutableStateOf<Stock?>(null) }

    val watchlistedSymbols = remember(watchlistStocks) {
        watchlistStocks.map { it.symbol }.toSet()
    }

    val filteredWatchlist = remember(watchlistStocks, watchlistSearchQuery) {
        if (watchlistSearchQuery.isBlank()) {
            watchlistStocks
        } else {
            watchlistStocks.filter {
                it.symbol.contains(watchlistSearchQuery, ignoreCase = true) ||
                        it.name.contains(watchlistSearchQuery, ignoreCase = true)
            }
        }
    }

    val filteredPriceAlerts = remember(priceAlerts, watchlistSearchQuery) {
        if (watchlistSearchQuery.isBlank()) {
            priceAlerts
        } else {
            priceAlerts.filter {
                it.symbol.contains(watchlistSearchQuery, ignoreCase = true) ||
                        it.name.contains(watchlistSearchQuery, ignoreCase = true)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 4.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "My Assets & Alerts",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = (-0.5).sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Monitor holdings and price thresholds",
                        style = MaterialTheme.typography.bodySmall,
                        color = VibeMutedText
                    )
                }
            }

            // Tab Switching Bar
            TabRow(
                selectedTabIndex = activeTab,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Tab(
                    selected = activeTab == 0,
                    onClick = { 
                        activeTab = 0 
                        watchlistSearchQuery = ""
                    },
                    text = { Text("Watchlist (${watchlistStocks.size})", fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = activeTab == 1,
                    onClick = { 
                        activeTab = 1 
                        watchlistSearchQuery = ""
                    },
                    text = { Text("Price Alerts (${priceAlerts.size})", fontWeight = FontWeight.Bold) }
                )
            }

            // Dynamic Search Bar
            val showSearchBar = if (activeTab == 0) watchlistStocks.isNotEmpty() else priceAlerts.isNotEmpty()
            if (showSearchBar) {
                SearchBar(
                    value = watchlistSearchQuery,
                    onValueChange = { watchlistSearchQuery = it },
                    placeholder = if (activeTab == 0) "Search in my watchlist..." else "Search price alerts...",
                    onClear = { watchlistSearchQuery = "" }
                )
            }

            // Tab Contents
            if (activeTab == 0) {
                // Watchlist View
                if (watchlistStocks.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            EmptyState(
                                title = "Your Watchlist is empty",
                                description = "Start exploring and click the heart icon on any stock, cryptocurrency, or commodity to add it here.",
                                icon = Icons.Default.Favorite
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = onNavigateToMarkets,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Explore Markets", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                } else if (filteredWatchlist.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        EmptyState(
                            title = "No matched watchlist items",
                            description = "No assets in your watchlist match \"$watchlistSearchQuery\".",
                            icon = Icons.Default.List
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(filteredWatchlist, key = { it.symbol }) { stock ->
                            MarketCard(
                                stock = stock,
                                isWatchlisted = true,
                                onWatchlistToggle = { viewModel.toggleWatchlist(stock) },
                                onClick = { selectedDetailedStock = stock }
                            )
                        }
                    }
                }
            } else {
                // Price Alerts View
                if (priceAlerts.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        ) {
                            EmptyState(
                                title = "No price alerts configured",
                                description = "Set custom triggers for your favorite stocks to be notified immediately of market breakouts or target dips.",
                                icon = Icons.Default.Notifications
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = onNavigateToMarkets,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Explore Stocks", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                } else if (filteredPriceAlerts.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        EmptyState(
                            title = "No matched price alerts",
                            description = "No configured alerts match \"$watchlistSearchQuery\".",
                            icon = Icons.Default.List
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(filteredPriceAlerts, key = { it.id }) { alert ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 6.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                ),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier
                                                    .size(36.dp)
                                                    .background(
                                                        if (alert.isActive) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                                        else VibeSurfaceVariant,
                                                        RoundedCornerShape(10.dp)
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Notifications,
                                                    contentDescription = null,
                                                    tint = if (alert.isActive) MaterialTheme.colorScheme.primary else VibeMutedText,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Column {
                                                Text(
                                                    text = alert.symbol,
                                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                                Text(
                                                    text = alert.name,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = VibeMutedText,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(12.dp))

                                        // Condition details
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Text(
                                                text = "Condition:",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = VibeMutedText
                                            )
                                            Text(
                                                text = if (alert.isAbove) "Goes Above (>=)" else "Goes Below (<=)",
                                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                                color = if (alert.isAbove) VibePositiveGreen else VibeNegativeRed
                                            )
                                            Text(
                                                text = "₹${String.format("%,.2f", alert.targetPrice)}",
                                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.ExtraBold),
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                        }

                                        // Current Price Helper
                                        val currentStock = viewModel.trendingStocks.find { it.symbol == alert.symbol } 
                                            ?: viewModel.topGainers.find { it.symbol == alert.symbol }
                                            ?: viewModel.topLosers.find { it.symbol == alert.symbol }
                                            ?: viewModel.getMarketStocks("Indian").find { it.symbol == alert.symbol }
                                            ?: viewModel.getMarketStocks("US").find { it.symbol == alert.symbol }
                                            ?: viewModel.getMarketStocks("Crypto").find { it.symbol == alert.symbol }
                                            ?: viewModel.getMarketStocks("Commodity").find { it.symbol == alert.symbol }

                                        if (currentStock != null) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                                modifier = Modifier.padding(top = 2.dp)
                                            ) {
                                                Text(
                                                    text = "Current Price:",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = VibeMutedText
                                                )
                                                Text(
                                                    text = "₹${String.format("%,.2f", currentStock.price)}",
                                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Switch(
                                            checked = alert.isActive,
                                            onCheckedChange = { isActive ->
                                                viewModel.togglePriceAlertActive(alert.id, isActive)
                                            },
                                            colors = SwitchDefaults.colors(
                                                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                                                checkedTrackColor = MaterialTheme.colorScheme.primary,
                                                uncheckedThumbColor = VibeMutedText,
                                                uncheckedTrackColor = VibeSurfaceVariant
                                            )
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))

                                        IconButton(
                                            onClick = { viewModel.removePriceAlert(alert.id) }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete Alert",
                                                tint = VibeNegativeRed.copy(alpha = 0.8f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Shared bottom sheets / dialogs
        selectedDetailedStock?.let { stock ->
            StockDetailsBottomSheet(
                stock = stock,
                isWatchlisted = watchlistedSymbols.contains(stock.symbol),
                onToggleWatchlist = { viewModel.toggleWatchlist(stock) },
                onSetPriceAlert = { showSetAlertForStock = stock },
                onClose = { selectedDetailedStock = null }
            )
        }

        showSetAlertForStock?.let { stock ->
            SetPriceAlertDialog(
                stock = stock,
                onDismiss = { showSetAlertForStock = null },
                onSaveAlert = { targetPrice, isAbove ->
                    viewModel.addPriceAlert(stock, targetPrice, isAbove)
                    showSetAlertForStock = null
                }
            )
        }
    }
}
