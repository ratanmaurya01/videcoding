package com.example.data

import com.example.data.model.Blog
import com.example.data.model.NotificationItem
import com.example.data.model.Stock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class VibeRepository(private val vibeDao: VibeDao) {

    fun getStocks(type: String): List<Stock> {
        return when (type) {
            "Indian" -> DummyData.dummyIndianStocks
            "US" -> DummyData.dummyUsStocks
            "Crypto" -> DummyData.dummyCrypto
            "Commodity" -> DummyData.dummyCommodities
            else -> emptyList()
        }
    }

    fun getAllStocks(): List<Stock> {
        return DummyData.dummyIndianStocks + DummyData.dummyUsStocks + DummyData.dummyCrypto + DummyData.dummyCommodities
    }

    fun getTrendingStocks(): List<Stock> {
        return getAllStocks().sortedByDescending { Math.abs(it.changePercent) }.take(8)
    }

    fun getTopGainers(): List<Stock> {
        return getAllStocks().filter { it.changePercent > 0 }.sortedByDescending { it.changePercent }.take(8)
    }

    fun getTopLosers(): List<Stock> {
        return getAllStocks().filter { it.changePercent < 0 }.sortedBy { it.changePercent }.take(8)
    }

    fun getBlogs(): List<Blog> {
        return DummyData.dummyBlogs
    }

    fun getBlogById(id: Int): Blog? {
        return DummyData.dummyBlogs.find { it.id == id }
    }

    fun getNotifications(): List<NotificationItem> {
        return DummyData.dummyNotifications
    }

    // Bookmarks via Room
    val bookmarkedBlogIds: Flow<Set<Int>> = vibeDao.getBookmarkedBlogIds()
        .map { list -> list.map { it.blogId }.toSet() }

    suspend fun addBookmark(blogId: Int) {
        vibeDao.addBookmark(BookmarkedBlogEntity(blogId))
    }

    suspend fun removeBookmark(blogId: Int) {
        vibeDao.removeBookmark(blogId)
    }

    fun isBlogBookmarked(blogId: Int): Flow<Boolean> = vibeDao.isBlogBookmarked(blogId)

    // Watchlist via Room
    val watchlistItems: Flow<List<WatchlistEntity>> = vibeDao.getWatchlistItems()

    suspend fun addToWatchlist(symbol: String, name: String, type: String) {
        vibeDao.addToWatchlist(WatchlistEntity(symbol = symbol, name = name, type = type))
    }

    suspend fun removeFromWatchlist(symbol: String) {
        vibeDao.removeFromWatchlist(symbol)
    }

    fun isStockInWatchlist(symbol: String): Flow<Boolean> = vibeDao.isStockInWatchlist(symbol)

    // Price Alerts via Room
    val priceAlerts: Flow<List<PriceAlertEntity>> = vibeDao.getPriceAlerts()

    suspend fun insertPriceAlert(symbol: String, name: String, targetPrice: Double, isAbove: Boolean) {
        vibeDao.insertPriceAlert(
            PriceAlertEntity(
                symbol = symbol,
                name = name,
                targetPrice = targetPrice,
                isAbove = isAbove
            )
        )
    }

    suspend fun deletePriceAlert(id: Int) {
        vibeDao.deletePriceAlert(id)
    }

    suspend fun togglePriceAlertActive(id: Int, isActive: Boolean) {
        vibeDao.togglePriceAlertActive(id, isActive)
    }
}
