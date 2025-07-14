package com.example.mobiledevelopmentproject.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.mobiledevelopmentproject.viewmodel.MovieViewModel

@Composable
fun MovieListScreen(
    viewModel: MovieViewModel = viewModel(),
    navController: NavController
) {
    val movies = viewModel.movies.collectAsState()
//    val query = viewModel.searchQuery.collectAsState()
    val searchText = viewModel.searchText.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        containerColor = Color(0xFF182945),
        contentColor = Color(0xFFf8f7fc)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            OutlinedTextField(
                value = searchText.value,
                onValueChange = { viewModel.onSearchTextChanged(it) },
                label = { Text("Search movie...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        viewModel.onSearchTriggered()
                        keyboardController?.hide()
                    }
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedContainerColor = Color(0xFF1c1a1a),
                    focusedContainerColor = Color(0xFF1c1a1a)
                )
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(movies.value.size) { index ->
                    val movie = movies.value[index]
                    MovieItem(
                        movie = movie,
                        onDetailClick = {
                            navController.navigate("movie_detail/${movie.id}")
                        }
                    )
                }
            }
        }
    }
}