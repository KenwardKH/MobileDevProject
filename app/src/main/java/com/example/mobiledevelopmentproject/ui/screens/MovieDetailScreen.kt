package com.example.mobiledevelopmentproject.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.mobiledevelopmentproject.data.local.WatchlistManager
import com.example.mobiledevelopmentproject.data.model.Movie
import com.example.mobiledevelopmentproject.data.model.MovieDetail
import com.example.mobiledevelopmentproject.data.model.toMovie
import com.example.mobiledevelopmentproject.ui.components.GlideImage
import com.example.mobiledevelopmentproject.viewmodel.MovieDetailViewModel
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(movieId: Int) {
    val viewModel: MovieDetailViewModel = viewModel()
    val movie = viewModel.movie.collectAsState()

    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetail(movieId)
    }

    val isInWatchlist = remember { mutableStateOf(false) }

    LaunchedEffect(movieId, movie.value) {
        movie.value?.let {
            isInWatchlist.value = WatchlistManager.isInWatchList(it.id)
        }
    }

    Scaffold (
        containerColor = Color(0xFF182945),
        contentColor = Color(0xFFf8f7fc),
    ) { padding ->
        val data = movie.value

        if (data != null) {
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {

                    val porterUrl = data.poster_path?.takeIf { it.isNotBlank() }?.let {
                        "https://image.tmdb.org/t/p/w500$it"
                    }

                    if (porterUrl != null){
                        GlideImage(
                            url = porterUrl,
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .height(350.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .border(1.dp, Color.Black)
                                .height(350.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "No Image Available",
                                color = Color.LightGray,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = data.title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                }

                item {
                    Text(
                        text = "Genres: ${if(data.genres.isNotEmpty()) data.genres.joinToString(", ") {it.name} else "N/A"}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = "Release Date: ${data.release_date.ifBlank { "N/A" }}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = "Language: ${data.original_language.ifBlank { "N/A" }.uppercase()}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = "Runtime: ${data.runtime?.toString() ?: "N/A"} minutes",
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Button(
                        onClick = {
                            data.let {
                                val movie = it.toMovie()
                                WatchlistManager.toggleMovie(movie)
                                isInWatchlist.value = WatchlistManager.isInWatchList(movie.id)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isInWatchlist.value) Color.Gray else Color(0xFF3A3F47),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = if (isInWatchlist.value) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (isInWatchlist.value) "Already in Watchlist" else "Move to Watchlist")
                    }

                }

                item {
                    Divider()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Rating: ${data.vote_average ?: "N/A"} (${data.vote_count ?: "N/A"} votes)",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = "Popularity: ${data.popularity ?: "N/A"}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Status: ${data.status.ifBlank { "N/A" }}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                data.tagline?.let {
                    item {
                        Text(
                            text = "\"$it\"",
                            style = MaterialTheme.typography.titleSmall,
                            color = Color(0xFFB0BEC5)
                        )
                    }
                }

                item {
                    Divider()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Synopsis",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = data.overview.ifBlank { "N/A" },
                        style = MaterialTheme.typography.bodyMedium
                    )

                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
