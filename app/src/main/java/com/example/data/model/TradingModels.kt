package com.example.data.model

data class Stock(
    val symbol: String,
    val name: String,
    val price: Double,
    val changePercent: Double,
    val high: Double,
    val low: Double,
    val volume: String,
    val type: String // "Indian", "US", "Crypto", "Commodity"
) {
    val changeAbsolute: Double get() = price * (changePercent / 100.0)
}

data class Blog(
    val id: Int,
    val title: String,
    val subtitle: String,
    val author: String,
    val date: String,
    val readTime: String,
    val imageUrl: String,
    val category: String,
    val description: String,
    val content: String,
    var likes: Int,
    val views: Int
)

data class NotificationItem(
    val id: Int,
    val title: String,
    val message: String,
    val timestamp: String,
    val category: String // "Market", "Alert", "Blog", "IPO"
)
