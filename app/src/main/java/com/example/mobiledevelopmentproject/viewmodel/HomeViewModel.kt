package com.example.mobiledevelopmentproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiledevelopmentproject.data.model.Movie
import com.example.mobiledevelopmentproject.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.mobiledevelopmentproject.BuildConfig


class HomeViewModel : ViewModel() {
    private val apiKey = BuildConfig.TMDB_API_KEY

    private val repository = MovieRepository()

    private val _nowPlaying = MutableStateFlow<List<Movie>>(emptyList())
    val nowPlaying: StateFlow<List<Movie>> = _nowPlaying

    private val _popular = MutableStateFlow<List<Movie>>(emptyList())
    val popular: StateFlow<List<Movie>> = _popular

    private val _topRated = MutableStateFlow<List<Movie>>(emptyList())
    val topRated: StateFlow<List<Movie>> = _topRated

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
      fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _nowPlaying.value = repository.getNowPlaying(apiKey).results
                _popular.value = repository.getPopularMovies(apiKey).results
                _topRated.value = repository.getTopRated(apiKey).results
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}