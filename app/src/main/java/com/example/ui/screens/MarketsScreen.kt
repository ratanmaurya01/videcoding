package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Stock
import com.example.ui.VibeViewModel
import com.example.ui.components.CategoryChip
import com.example.ui.components.EmptyState
import com.example.ui.components.MarketCard
import com.example.ui.components.SearchBar
import com.example.ui.theme.*

@Composable
fun MarketsScreen(
    viewModel: VibeViewModel,
    modifier: Modifier = Modifier
) {
    val selectedType by viewModel.selectedMarketType.collectAsState()
    val watchlistEntityItems by viewModel.watchlistEntityItems.collectAsState()
    val watchlistedSymbols = remember(watchlistEntityItems) {
        watchlistEntityItems.map { it.symbol }.toSet()
    }

    var marketSearchQuery by remember { mutableStateOf("") }
    var selectedDetailedStock by remember { mutableStateOf<Stock?>(null) }
    var showSetAlertForStock by remember { mutableStateOf<Stock?>(null) }

    val rawStocks = remember(selectedType) {
        viewModel.getMarketStocks(selectedType)
    }

    val filteredStocks = remember(rawStocks, marketSearchQuery) {
        if (marketSearchQuery.isBlank()) {
            rawStocks
        } else {
            rawStocks.filter {
                it.symbol.contains(marketSearchQuery, ignoreCase = true) ||
                        it.name.contains(marketSearchQuery, ignoreCase = true)
            }
        }
    }

    val marketTabs = listOf("Indian", "US", "Crypto", "Commodity")

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
                    .padding(top = 24.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Markets Overview",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = (-0.5).sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Real-time mock stats tracker",
                        style = MaterialTheme.typography.bodySmall,
                        color = VibeMutedText
                    )
                }
            }

            // Search Bar
            SearchBar(
                value = marketSearchQuery,
                onValueChange = { marketSearchQuery = it },
                placeholder = "Search in $selectedType market...",
                onClear = { marketSearchQuery = "" }
            )

            // Market Type Chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(marketTabs) { tab ->
                    CategoryChip(
                        name = when (tab) {
                            "Indian" -> "🇮🇳 Indian Market"
                            "US" -> "🇺🇸 US Market"
                            "Crypto" -> "🪙 Crypto"
                            "Commodity" -> "📦 Commodities"
                            else -> tab
                        },
                        selected = selectedType == tab,
                        onSelected = {
                            viewModel.selectMarketType(tab)
                            marketSearchQuery = ""
                        }
                    )
                }
            }

            // Stock list
            if (filteredStocks.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    EmptyState(
                        title = "No assets found",
                        description = "No assets matched your search query in the $selectedType market.",
                        icon = Icons.Default.ShowChart
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(filteredStocks, key = { it.symbol }) { stock ->
                        val isFav = watchlistedSymbols.contains(stock.symbol)
                        MarketCard(
                            stock = stock,
                            isWatchlisted = isFav,
                            onWatchlistToggle = { viewModel.toggleWatchlist(stock) },
                            onClick = { selectedDetailedStock = stock }
                        )
                    }
                }
            }
        }

        // Detailed stock modal sheet
        selectedDetailedStock?.let { stock ->
            StockDetailsBottomSheet(
                stock = stock,
                isWatchlisted = watchlistedSymbols.contains(stock.symbol),
                onToggleWatchlist = { viewModel.toggleWatchlist(stock) },
                onSetPriceAlert = { showSetAlertForStock = stock },
                onClose = { selectedDetailedStock = null }
            )
        }

        // Custom Price Alert Modal Dialog
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

@Composable
fun StockDetailsBottomSheet(
    stock: Stock,
    isWatchlisted: Boolean,
    onToggleWatchlist: () -> Unit,
    onSetPriceAlert: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPositive = stock.changePercent >= 0
    val trendColor = if (isPositive) VibePositiveGreen else VibeNegativeRed

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable { onClose() },
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = false) {}
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Drag handle
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .background(VibeSurfaceVariant, RoundedCornerShape(2.dp))
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Header details
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stock.symbol,
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = stock.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = VibeMutedText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Market: ${stock.type} Asset Class",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Row {
                        IconButton(
                            onClick = onSetPriceAlert,
                            modifier = Modifier
                                .size(44.dp)
                                .background(VibeSurfaceVariant, RoundedCornerShape(12.dp))
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddAlert,
                                contentDescription = "Set Price Alert",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = onToggleWatchlist,
                            modifier = Modifier
                                .size(44.dp)
                                .background(VibeSurfaceVariant, RoundedCornerShape(12.dp))
                        ) {
                            Icon(
                                imageVector = if (isWatchlisted) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Watchlist toggle",
                                tint = if (isWatchlisted) VibeNegativeRed else VibeMutedText
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = onClose,
                            modifier = Modifier
                                .size(44.dp)
                                .background(VibeSurfaceVariant, RoundedCornerShape(12.dp))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Pricing
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "CURRENT PRICE",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = VibeMutedText
                        )
                        Text(
                            text = "₹${String.format("%,.2f", stock.price)}",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = (-1).sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "DAILY CHANGE",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = VibeMutedText
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isPositive) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = trendColor,
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = "${if (isPositive) "+" else ""}${String.format("%.2f", stock.changePercent)}%",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = trendColor
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Divider(color = VibeSurfaceVariant)
                Spacer(modifier = Modifier.height(24.dp))

                // Key statistics grid
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        StatRow(label = "Daily High", value = "₹${String.format("%,.2f", stock.high)}")
                        Spacer(modifier = Modifier.height(16.dp))
                        StatRow(label = "Trading Volume", value = stock.volume)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        StatRow(label = "Daily Low", value = "₹${String.format("%,.2f", stock.low)}")
                        Spacer(modifier = Modifier.height(16.dp))
                        StatRow(label = "Asset Type", value = stock.type)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Educational alert hint
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.TrendingUp,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Educational Notice: Tap the heart icon to add/remove this asset in your Watchlist reactively.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun StatRow(label: String, value: String) {
    Column {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = VibeMutedText
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetPriceAlertDialog(
    stock: Stock,
    onDismiss: () -> Unit,
    onSaveAlert: (targetPrice: Double, isAbove: Boolean) -> Unit
) {
    var priceInput by remember { mutableStateOf(stock.price.toString()) }
    var isAbove by remember { mutableStateOf(true) }
    var showError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AddAlert,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Set Price Alert",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Configure a price alert trigger for ${stock.symbol} (${stock.name}). We will alert you once the target price condition is met.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = VibeMutedText
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "CURRENT PRICE",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = VibeMutedText
                        )
                        Text(
                            text = "₹${String.format("%,.2f", stock.price)}",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                OutlinedTextField(
                    value = priceInput,
                    onValueChange = {
                        priceInput = it
                        showError = false
                    },
                    label = { Text("Target Price (₹)") },
                    placeholder = { Text(stock.price.toString()) },
                    singleLine = true,
                    isError = showError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )

                if (showError) {
                    Text(
                        text = "Please enter a valid numeric target price.",
                        style = MaterialTheme.typography.labelSmall,
                        color = VibeNegativeRed
                    )
                }

                Text(
                    text = "ALERT TRIGGER DIRECTION",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = VibeMutedText
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = isAbove,
                        onClick = { isAbove = true },
                        label = { Text("Goes Above (>=)") },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = !isAbove,
                        onClick = { isAbove = false },
                        label = { Text("Goes Below (<=)") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val parsedPrice = priceInput.toDoubleOrNull()
                    if (parsedPrice != null && parsedPrice > 0.0) {
                        onSaveAlert(parsedPrice, isAbove)
                    } else {
                        showError = true
                    }
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Create Alert", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = VibeMutedText)
            }
        }
    )
}
