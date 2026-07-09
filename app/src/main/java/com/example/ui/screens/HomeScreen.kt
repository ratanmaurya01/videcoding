package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Blog
import com.example.data.model.Stock
import com.example.ui.VibeViewModel
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    viewModel: VibeViewModel,
    onNavigateToBlogDetail: (Int) -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToMarkets: () -> Unit,
    modifier: Modifier = Modifier
) {
    val username by viewModel.username.collectAsState()
    val searchVal by viewModel.globalSearchQuery.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val filteredStocks by viewModel.filteredStocks.collectAsState()

    val watchlistEntityItems by viewModel.watchlistEntityItems.collectAsState()
    val watchlistedSymbols = remember(watchlistEntityItems) {
        watchlistEntityItems.map { it.symbol }.toSet()
    }

    // Indices data
    val indices = remember {
        listOf(
            IndexData("NIFTY 50", 24125.80, 1.12),
            IndexData("SENSEX", 79240.50, 0.95),
            IndexData("BANK NIFTY", 51840.20, 1.45)
        )
    }

    // Latest 2 news/notifications
    val latestNews = remember { viewModel.notificationsList.take(2) }

    // Featured blog (id 1) and recommended (id 2, 3)
    val featuredBlog = remember { viewModel.allBlogsList.find { it.id == 1 } ?: viewModel.allBlogsList.first() }
    val recommendedBlogs = remember { viewModel.allBlogsList.filter { it.id in listOf(2, 3) } }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            HomeHeader(
                username = username,
                onNotificationClick = onNavigateToNotifications
            )

            SearchBar(
                value = searchVal,
                onValueChange = { viewModel.setGlobalSearchQuery(it) },
                placeholder = "Search stocks (e.g. RELIANCE, TSLA, BTC)...",
                onClear = { viewModel.setGlobalSearchQuery("") }
            )

            if (isRefreshing) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth().height(2.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = Color.Transparent
                )
            }

            // If searching, show search results
            if (searchVal.isNotBlank()) {
                SearchResultsList(
                    filteredStocks = filteredStocks,
                    watchlistedSymbols = watchlistedSymbols,
                    onToggleWatchlist = { viewModel.toggleWatchlist(it) },
                    onStockClick = { viewModel.toggleWatchlist(it) }
                )
            } else {
                // Primary dashboard
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    // Pull to refresh trigger simulated
                    item {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.refreshData() }
                                .padding(vertical = 4.dp),
                            color = Color.Transparent
                        ) {
                            Text(
                                text = "💡 Tap here to pull & refresh market data",
                                style = MaterialTheme.typography.bodySmall,
                                color = VibeMutedText,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }

                    // Indices Row
                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(indices) { index ->
                                IndexCard(index = index)
                            }
                        }
                    }

                    // Trending Stocks
                    item {
                        SectionHeader(
                            title = "Trending Stocks",
                            actionText = "See All",
                            onActionClick = onNavigateToMarkets
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(viewModel.trendingStocks) { stock ->
                                val isFav = watchlistedSymbols.contains(stock.symbol)
                                StockCard(
                                    stock = stock,
                                    onClick = { viewModel.toggleWatchlist(stock) },
                                    trailingContent = {
                                        Icon(
                                            imageVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                            contentDescription = null,
                                            tint = if (isFav) VibeNegativeRed else VibeMutedText,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                )
                            }
                        }
                    }

                    // Top Gainers
                    item {
                        SectionHeader(
                            title = "Top Gainers 🚀",
                            actionText = "See All",
                            onActionClick = onNavigateToMarkets
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(viewModel.topGainers) { stock ->
                                val isFav = watchlistedSymbols.contains(stock.symbol)
                                StockCard(
                                    stock = stock,
                                    onClick = { viewModel.toggleWatchlist(stock) },
                                    trailingContent = {
                                        Icon(
                                            imageVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                            contentDescription = null,
                                            tint = if (isFav) VibeNegativeRed else VibeMutedText,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                )
                            }
                        }
                    }

                    // Top Losers
                    item {
                        SectionHeader(
                            title = "Top Losers 📉",
                            actionText = "See All",
                            onActionClick = onNavigateToMarkets
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(viewModel.topLosers) { stock ->
                                val isFav = watchlistedSymbols.contains(stock.symbol)
                                StockCard(
                                    stock = stock,
                                    onClick = { viewModel.toggleWatchlist(stock) },
                                    trailingContent = {
                                        Icon(
                                            imageVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                            contentDescription = null,
                                            tint = if (isFav) VibeNegativeRed else VibeMutedText,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                )
                            }
                        }
                    }

                    // Latest News
                    item {
                        SectionHeader(title = "Latest News & Events")
                    }
                    items(latestNews) { news ->
                        NewsCard(
                            title = news.title,
                            message = news.message,
                            time = news.timestamp,
                            category = news.category,
                            onClick = onNavigateToNotifications
                        )
                    }

                    // Featured Blog
                    item {
                        SectionHeader(title = "Featured Blog")
                        BlogCard(
                            blog = featuredBlog,
                            onClick = { onNavigateToBlogDetail(featuredBlog.id) }
                        )
                    }

                    // Recommended Articles
                    item {
                        SectionHeader(title = "Recommended For You")
                    }
                    items(recommendedBlogs) { blog ->
                        BlogCard(
                            blog = blog,
                            onClick = { onNavigateToBlogDetail(blog.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeHeader(
    username: String,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
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
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Welcome back,",
                    style = MaterialTheme.typography.bodySmall,
                    color = VibeMutedText
                )
                Text(
                    text = username,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Box(
            modifier = Modifier
                .size(44.dp)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .clickable { onNotificationClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(22.dp)
            )
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .align(Alignment.TopEnd)
                    .padding(top = 1.dp, end = 1.dp)
                    .background(VibePrimary, CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.background, CircleShape)
            )
        }
    }
}

data class IndexData(val name: String, val price: Double, val change: Double)

@Composable
fun IndexCard(index: IndexData) {
    val isPositive = index.change >= 0
    val trendColor = if (isPositive) VibePositiveGreen else VibeNegativeRed

    Card(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = index.name,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = VibeMutedText
            )

            Column {
                Text(
                    text = "₹${String.format("%,.2f", index.price)}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isPositive) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = trendColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "${if (isPositive) "+" else ""}${index.change}%",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = trendColor
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun SearchResultsList(
    filteredStocks: List<Stock>,
    watchlistedSymbols: Set<String>,
    onToggleWatchlist: (Stock) -> Unit,
    onStockClick: (Stock) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        item {
            Text(
                text = "Search Results",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        if (filteredStocks.isEmpty()) {
            item {
                EmptyState(
                    title = "No results found",
                    description = "Try searching for a different stock name, ticker, or coin symbol",
                    icon = Icons.Default.TrendingUp
                )
            }
        } else {
            items(filteredStocks) { stock ->
                val isFav = watchlistedSymbols.contains(stock.symbol)
                MarketCard(
                    stock = stock,
                    isWatchlisted = isFav,
                    onWatchlistToggle = { onToggleWatchlist(stock) },
                    onClick = { onStockClick(stock) }
                )
            }
        }
    }
}
