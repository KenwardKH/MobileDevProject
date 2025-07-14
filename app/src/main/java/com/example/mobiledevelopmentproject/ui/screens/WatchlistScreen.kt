package com.example.mobiledevelopmentproject.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobiledevelopmentproject.data.local.WatchlistManager
import com.example.mobiledevelopmentproject.ui.components.GlideImage

@Composable
fun WatchlistScreen (navController: NavController) {
    val watchlist = remember { mutableStateOf(WatchlistManager.getWatchlist()) }
    if (watchlist.value.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Watchlist is Empty",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFFE0E0E0)
            )
        }
    } else {
        LazyColumn (modifier = Modifier.padding(8.dp)) {
            items(watchlist.value.size) { index ->
                val movie = watchlist.value[index]
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("movie_detail/${movie.id}")
                        }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val posterUrl = movie.poster_path?.takeIf { it.isNotBlank() }?.let {
                        "https://image.tmdb.org/t/p/w500$it"
                    }
                    if (posterUrl != null){
                        GlideImage(
                            url = posterUrl,
                            modifier = Modifier
                                .height(120.dp)
                                .width(80.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .border(1.dp, Color.Black)
                                .height(120.dp)
                                .width(80.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "No Image Available",
                                color = Color.LightGray,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))
                    Column (modifier = Modifier.weight(1f)) {
                        Text(movie.title, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Rating: ${movie.vote_average} (${movie.vote_count} votes)",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Release Date: ${movie.release_date.ifBlank { "N/A" }}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    IconButton(onClick = {
                        WatchlistManager.removeMovie(movie.id)
                        watchlist.value = WatchlistManager.getWatchlist()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Remove")
                    }
                }
            }
        }
    }
}