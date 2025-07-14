package com.example.mobiledevelopmentproject.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.mobiledevelopmentproject.data.model.Movie
import com.example.mobiledevelopmentproject.ui.components.GlideImage

@Composable
fun MovieItem(movie: Movie, onDetailClick: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .clickable {
            onDetailClick()
        }
    ){
        Row (
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            val porterUrl = movie.poster_path?.takeIf { it.isNotBlank() }?.let {
                "https://image.tmdb.org/t/p/w500$it"
            }
            if(porterUrl != null){
                GlideImage(
                    url = porterUrl,
                    modifier = Modifier
                        .size(100.dp, height = 150.dp)
                        .aspectRatio(2f / 3f)
                )
            } else {
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Black)
                        .size(100.dp, height = 150.dp)
                        .aspectRatio(2f / 3f),
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
            Column (
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(movie.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${movie.overview.ifBlank { "No synopsis available" }}",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 4,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Release Date: ${movie.release_date.ifBlank { "N/A" }}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
            thickness = 3.dp,
            modifier = Modifier.padding(top = 12.dp)
        )
    }

}