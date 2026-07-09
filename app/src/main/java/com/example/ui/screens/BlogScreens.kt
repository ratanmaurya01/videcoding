package com.example.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.Blog
import com.example.ui.VibeViewModel
import com.example.ui.components.BlogCard
import com.example.ui.components.CategoryChip
import com.example.ui.components.EmptyState
import com.example.ui.components.SearchBar
import com.example.ui.theme.*

@Composable
fun BlogFeedScreen(
    viewModel: VibeViewModel,
    onNavigateToBlogDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedCategory by viewModel.selectedBlogCategory.collectAsState()
    val blogSearchQuery by viewModel.blogSearchQuery.collectAsState()
    val filteredBlogs by viewModel.filteredBlogs.collectAsState()

    val categoriesList = listOf(
        "All", "Trading", "Investing", "Mutual Funds", "IPO",
        "Swing Trading", "Intraday", "Options", "Futures", "Finance", "Psychology"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
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
                    text = "Vibe Academy",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-0.5).sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Sharpen your trading knowledge",
                    style = MaterialTheme.typography.bodySmall,
                    color = VibeMutedText
                )
            }
        }

        // Search Blogs
        SearchBar(
            value = blogSearchQuery,
            onValueChange = { viewModel.setBlogSearchQuery(it) },
            placeholder = "Search premium articles & lessons...",
            onClear = { viewModel.setBlogSearchQuery("") }
        )

        // Categories Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(categoriesList) { category ->
                CategoryChip(
                    name = category,
                    selected = selectedCategory == category,
                    onSelected = { viewModel.selectBlogCategory(category) }
                )
            }
        }

        // Blog list
        if (filteredBlogs.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                EmptyState(
                    title = "No articles found",
                    description = "No articles match your search or filter requirements.",
                    icon = Icons.Default.Book
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(filteredBlogs, key = { it.id }) { blog ->
                    BlogCard(
                        blog = blog,
                        onClick = { onNavigateToBlogDetail(blog.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun BlogDetailScreen(
    blogId: Int,
    viewModel: VibeViewModel,
    onBack: () -> Unit,
    onNavigateToBlogDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val blog = remember(blogId) { viewModel.allBlogsList.find { it.id == blogId } }

    if (blog == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Article not found.", color = MaterialTheme.colorScheme.onBackground)
        }
        return
    }

    val isBookmarked by viewModel.isBlogBookmarked(blog.id).collectAsState(initial = false)

    // Local Like Toggle state
    var localLiked by remember(blogId) { mutableStateOf(false) }
    var localLikesCount by remember(blogId) { mutableStateOf(blog.likes) }

    // Related Articles: select up to 2 other articles in the same category or overall
    val relatedBlogs = remember(blogId) {
        viewModel.allBlogsList
            .filter { it.id != blogId && (it.category == blog.category) }
            .take(2)
            .ifEmpty {
                viewModel.allBlogsList.filter { it.id != blogId }.take(2)
            }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.surface, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row {
                    IconButton(
                        onClick = {
                            viewModel.toggleBookmark(blog.id)
                            Toast.makeText(
                                context,
                                if (isBookmarked) "Removed from bookmarks" else "Added to bookmarks",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .background(MaterialTheme.colorScheme.surface, CircleShape)
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) VibePrimary else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, blog.title)
                                putExtra(Intent.EXTRA_TEXT, "${blog.title}\n\nRead more on VibeTrading app!")
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share Article"))
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .background(MaterialTheme.colorScheme.surface, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Article Content (Scrollable)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // Large Cover Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                ) {
                    AsyncImage(
                        model = blog.imageUrl,
                        contentDescription = blog.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                                )
                            )
                    )
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                            .align(Alignment.BottomStart)
                    ) {
                        Text(
                            text = blog.category,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = VibeOnPrimary,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }

                // Title and details
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = blog.title,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            lineHeight = 36.sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = blog.subtitle,
                        style = MaterialTheme.typography.titleMedium,
                        color = VibeMutedText,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Author Metadata card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(VibeSurfaceVariant, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = blog.author,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = blog.date,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = VibeMutedText
                                    )
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.AccessTime,
                                    contentDescription = null,
                                    tint = VibeMutedText,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = blog.readTime,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                                    color = VibeMutedText
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Like action bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(VibeSurfaceVariant.copy(alpha = 0.5f))
                                .clickable {
                                    localLiked = !localLiked
                                    localLikesCount = if (localLiked) blog.likes + 1 else blog.likes
                                }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                imageVector = if (localLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                                contentDescription = "Like",
                                tint = if (localLiked) VibePrimary else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "$localLikesCount Likes",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.Visibility,
                                contentDescription = "Views",
                                tint = VibeMutedText,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${blog.views} Views",
                                style = MaterialTheme.typography.bodyMedium,
                                color = VibeMutedText
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Divider(color = VibeSurfaceVariant)
                    Spacer(modifier = Modifier.height(24.dp))

                    // Formatted blog content
                    BlogContentRenderer(content = blog.content)

                    Spacer(modifier = Modifier.height(32.dp))
                    Divider(color = VibeSurfaceVariant)
                    Spacer(modifier = Modifier.height(24.dp))

                    // Related Articles section
                    if (relatedBlogs.isNotEmpty()) {
                        Text(
                            text = "Related Articles",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        relatedBlogs.forEach { related ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .clickable { onNavigateToBlogDetail(related.id) },
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
                                    AsyncImage(
                                        model = related.imageUrl,
                                        contentDescription = related.title,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(72.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = related.title,
                                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = related.category,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = related.readTime,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = VibeMutedText
                                        )
                                    }
                                    Icon(
                                        imageVector = Icons.Default.ChevronRight,
                                        contentDescription = null,
                                        tint = VibeMutedText
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

@Composable
fun BlogContentRenderer(content: String) {
    val paragraphs = content.split("\n\n")

    Column(modifier = Modifier.fillMaxWidth()) {
        paragraphs.forEach { paragraph ->
            val trimmed = paragraph.trim()
            if (trimmed.startsWith("###")) {
                // Header style
                Text(
                    text = trimmed.replace("###", "").trim(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            } else if (trimmed.startsWith("1.") || trimmed.startsWith("-")) {
                // List style
                Text(
                    text = trimmed,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(vertical = 4.dp),
                    lineHeight = 26.sp
                )
            } else {
                // Standard paragraph
                Text(
                    text = trimmed,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(vertical = 8.dp),
                    lineHeight = 26.sp
                )
            }
        }
    }
}
