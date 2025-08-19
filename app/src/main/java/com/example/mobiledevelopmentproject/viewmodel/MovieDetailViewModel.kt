package com.example.mobiledevelopmentproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiledevelopmentproject.BuildConfig
import com.example.mobiledevelopmentproject.data.model.MovieDetail
import com.example.mobiledevelopmentproject.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {
    private val apiKey = BuildConfig.TMDB_API_KEY

    private val repository = MovieRepository()

    private val _movie = MutableStateFlow<MovieDetail?>(null)
    val movie: StateFlow<MovieDetail?> = _movie

    fun fetchMovieDetail(movieId: Int){
        viewModelScope.launch {
            try {
                val result = repository.getMovieDetail(movieId, apiKey)
                _movie.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}