package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.VibeRepository
import com.example.data.PriceAlertEntity
import com.example.data.model.Blog
import com.example.data.model.NotificationItem
import com.example.data.model.Stock
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class VibeViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = VibeRepository(db.vibeDao())

    // Auth State
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _username = MutableStateFlow("TraderOne")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _email = MutableStateFlow("trader.one@vibe.com")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _authScreen = MutableStateFlow("login") // "login", "register", "forgot"
    val authScreen: StateFlow<String> = _authScreen.asStateFlow()

    // App Preferences
    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _pushNotificationsEnabled = MutableStateFlow(true)
    val pushNotificationsEnabled: StateFlow<Boolean> = _pushNotificationsEnabled.asStateFlow()

    private val _selectedLanguage = MutableStateFlow("English")
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()

    // Pull to Refresh State
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    // Search and Filtering State
    private val _globalSearchQuery = MutableStateFlow("")
    val globalSearchQuery: StateFlow<String> = _globalSearchQuery.asStateFlow()

    private val _blogSearchQuery = MutableStateFlow("")
    val blogSearchQuery: StateFlow<String> = _blogSearchQuery.asStateFlow()

    private val _selectedBlogCategory = MutableStateFlow("All")
    val selectedBlogCategory: StateFlow<String> = _selectedBlogCategory.asStateFlow()

    private val _selectedMarketType = MutableStateFlow("Indian") // "Indian", "US", "Crypto", "Commodity"
    val selectedMarketType: StateFlow<String> = _selectedMarketType.asStateFlow()

    // Watchlist (Room State)
    val watchlistEntityItems = repository.watchlistItems.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Derived full stock watchlist items
    val watchlistStocks: StateFlow<List<Stock>> = combine(
        watchlistEntityItems,
        flowOf(repository.getAllStocks())
    ) { watchlistList, allStocks ->
        watchlistList.mapNotNull { entity ->
            allStocks.find { it.symbol == entity.symbol }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Bookmarked Blogs (Room State)
    val bookmarkedBlogIdsSet = repository.bookmarkedBlogIds.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptySet()
    )

    // Derived full bookmarked blogs
    val bookmarkedBlogs: StateFlow<List<Blog>> = combine(
        bookmarkedBlogIdsSet,
        flowOf(repository.getBlogs())
    ) { bookmarkIds, allBlogs ->
        allBlogs.filter { it.id in bookmarkIds }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Base Stock & Blog Data
    val trendingStocks: List<Stock> = repository.getTrendingStocks()
    val topGainers: List<Stock> = repository.getTopGainers()
    val topLosers: List<Stock> = repository.getTopLosers()
    val allBlogsList: List<Blog> = repository.getBlogs()
    val notificationsList: List<NotificationItem> = repository.getNotifications()

    // Filtered stocks based on global search in Home
    val filteredStocks: StateFlow<List<Stock>> = _globalSearchQuery
        .map { query ->
            if (query.isBlank()) {
                emptyList()
            } else {
                repository.getAllStocks().filter {
                    it.symbol.contains(query, ignoreCase = true) ||
                            it.name.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Filtered blogs based on search and category
    val filteredBlogs: StateFlow<List<Blog>> = combine(
        _blogSearchQuery,
        _selectedBlogCategory
    ) { query, category ->
        var blogs = repository.getBlogs()
        if (category != "All") {
            blogs = blogs.filter { it.category.equals(category, ignoreCase = true) }
        }
        if (query.isNotBlank()) {
            blogs = blogs.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.subtitle.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
        }
        blogs
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Auth Actions
    fun login(user: String, pass: String): Boolean {
        if (user.isNotBlank() && pass.isNotBlank()) {
            _username.value = user
            _email.value = if (user.contains("@")) user else "$user@vibetrading.com"
            _isLoggedIn.value = true
            return true
        }
        return false
    }

    fun register(user: String, emailAddr: String, pass: String): Boolean {
        if (user.isNotBlank() && emailAddr.isNotBlank() && pass.isNotBlank()) {
            _username.value = user
            _email.value = emailAddr
            _isLoggedIn.value = true
            return true
        }
        return false
    }

    fun skipLogin() {
        _username.value = "Guest Trader"
        _email.value = "guest@vibetrading.com"
        _isLoggedIn.value = true
    }

    fun logout() {
        _isLoggedIn.value = false
        _authScreen.value = "login"
    }

    fun setAuthScreen(screen: String) {
        _authScreen.value = screen
    }

    // Toggle Preferences
    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun toggleNotifications() {
        _pushNotificationsEnabled.value = !_pushNotificationsEnabled.value
    }

    fun setLanguage(lang: String) {
        _selectedLanguage.value = lang
    }

    // Search and Filters
    fun setGlobalSearchQuery(query: String) {
        _globalSearchQuery.value = query
    }

    fun setBlogSearchQuery(query: String) {
        _blogSearchQuery.value = query
    }

    fun selectBlogCategory(category: String) {
        _selectedBlogCategory.value = category
    }

    fun selectMarketType(type: String) {
        _selectedMarketType.value = type
    }

    // Pull to Refresh Action
    fun refreshData() {
        viewModelScope.launch {
            _isRefreshing.value = true
            delay(1000)
            _isRefreshing.value = false
        }
    }

    // Get specific market stocks
    fun getMarketStocks(type: String): List<Stock> {
        return repository.getStocks(type)
    }

    // Watchlist Actions
    fun isStockInWatchlist(symbol: String): Flow<Boolean> {
        return repository.isStockInWatchlist(symbol)
    }

    fun toggleWatchlist(stock: Stock) {
        viewModelScope.launch {
            val exists = repository.isStockInWatchlist(stock.symbol).first()
            if (exists) {
                repository.removeFromWatchlist(stock.symbol)
            } else {
                repository.addToWatchlist(stock.symbol, stock.name, stock.type)
            }
        }
    }

    // Bookmarks Actions
    fun isBlogBookmarked(blogId: Int): Flow<Boolean> {
        return repository.isBlogBookmarked(blogId)
    }

    fun toggleBookmark(blogId: Int) {
        viewModelScope.launch {
            val exists = repository.isBlogBookmarked(blogId).first()
            if (exists) {
                repository.removeBookmark(blogId)
            } else {
                repository.addBookmark(blogId)
            }
        }
    }

    // Price Alerts Actions (Zustand style global state via StateFlow)
    val priceAlerts: StateFlow<List<PriceAlertEntity>> = repository.priceAlerts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addPriceAlert(stock: Stock, targetPrice: Double, isAbove: Boolean) {
        viewModelScope.launch {
            repository.insertPriceAlert(stock.symbol, stock.name, targetPrice, isAbove)
        }
    }

    fun removePriceAlert(id: Int) {
        viewModelScope.launch {
            repository.deletePriceAlert(id)
        }
    }

    fun togglePriceAlertActive(id: Int, isActive: Boolean) {
        viewModelScope.launch {
            repository.togglePriceAlertActive(id, isActive)
        }
    }
}
