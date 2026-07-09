package com.example.data

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "blog_bookmarks")
data class BookmarkedBlogEntity(
    @PrimaryKey val blogId: Int,
    val bookmarkedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "watchlist")
data class WatchlistEntity(
    @PrimaryKey val symbol: String,
    val name: String,
    val type: String, // "Indian", "US", "Crypto", "Commodity"
    val addedAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
)

@Entity(tableName = "price_alerts")
data class PriceAlertEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val symbol: String,
    val name: String,
    val targetPrice: Double,
    val isAbove: Boolean, // true if trigger is above, false if trigger is below
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

@Dao
interface VibeDao {
    @Query("SELECT * FROM blog_bookmarks ORDER BY bookmarkedAt DESC")
    fun getBookmarkedBlogIds(): Flow<List<BookmarkedBlogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmark: BookmarkedBlogEntity)

    @Query("DELETE FROM blog_bookmarks WHERE blogId = :blogId")
    suspend fun removeBookmark(blogId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM blog_bookmarks WHERE blogId = :blogId LIMIT 1)")
    fun isBlogBookmarked(blogId: Int): Flow<Boolean>

    @Query("SELECT * FROM watchlist ORDER BY addedAt DESC")
    fun getWatchlistItems(): Flow<List<WatchlistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWatchlist(item: WatchlistEntity)

    @Query("DELETE FROM watchlist WHERE symbol = :symbol")
    suspend fun removeFromWatchlist(symbol: String)

    @Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE symbol = :symbol LIMIT 1)")
    fun isStockInWatchlist(symbol: String): Flow<Boolean>

    @Query("SELECT * FROM price_alerts ORDER BY createdAt DESC")
    fun getPriceAlerts(): Flow<List<PriceAlertEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPriceAlert(alert: PriceAlertEntity)

    @Query("DELETE FROM price_alerts WHERE id = :id")
    suspend fun deletePriceAlert(id: Int)

    @Query("UPDATE price_alerts SET isActive = :isActive WHERE id = :id")
    suspend fun togglePriceAlertActive(id: Int, isActive: Boolean)
}

@Database(entities = [BookmarkedBlogEntity::class, WatchlistEntity::class, PriceAlertEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vibeDao(): VibeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vibe_trading_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
