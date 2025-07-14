package com.example.mobiledevelopmentproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiledevelopmentproject.data.model.MovieDetail
import com.example.mobiledevelopmentproject.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {
    private val repository = MovieRepository()

    private val _movie = MutableStateFlow<MovieDetail?>(null)
    val movie: StateFlow<MovieDetail?> = _movie

    fun fetchMovieDetail(movieId: Int){
        viewModelScope.launch {
            try {
                val result = repository.getMovieDetail(movieId, "3945b26c802bbcc0b6e70aff903cde68")
                _movie.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}