package com.example.mobiledevelopmentproject.ui.screens

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mobiledevelopmentproject.data.model.Movie
import com.example.mobiledevelopmentproject.ui.components.GlideImage
import com.example.mobiledevelopmentproject.viewmodel.HomeViewModel


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val nowPlaying by viewModel.nowPlaying.collectAsState()
    val popular by viewModel.popular.collectAsState()
    val topRated by viewModel.topRated.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                MovieSection("Now Playing", nowPlaying, navController)
            }
            item {
                MovieSection("Popular", popular, navController)
            }
            item {
                MovieSection("Top Rated", topRated, navController)
            }
        }
    }
}

@Composable
fun MovieSection(title: String, movies: List<Movie>, navController: NavController) {
    Column (modifier = Modifier.padding(start = 16.dp, bottom = 24.dp, top = 16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Yellow,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow {
            items(movies.size) { index ->
                val movie = movies[index]

                Card(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .width(160.dp)
                        .clickable { navController.navigate("movie_detail/${movie.id}") },
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1C1C1E)
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        val posterUrl = movie.poster_path?.takeIf { it.isNotBlank() }?.let {
                            "https://image.tmdb.org/t/p/w500$it"
                        }
                        if (posterUrl != null){
                            GlideImage(
                                url = posterUrl,
                                modifier = Modifier
                                    .height(240.dp)
                                    .fillMaxWidth()
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .border(1.dp, Color.Black)
                                    .height(240.dp)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = "No Image Available",
                                    color = Color.LightGray,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = movie.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                        Row (
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = Color.Yellow,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            movie.vote_average?.let {
                                Text(
                                    text = "${String.format("%.2f", it)}",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }

            }
        }
    }
}