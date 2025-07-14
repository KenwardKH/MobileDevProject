package com.example.mobiledevelopmentproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiledevelopmentproject.data.model.Movie
import com.example.mobiledevelopmentproject.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val repository = MovieRepository()
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    val movies: StateFlow<List<Movie>> = _movies
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        getPopularMovies()
    }

    private fun getPopularMovies(){
        viewModelScope.launch {
            try {
                val response = repository.getPopularMovies("3945b26c802bbcc0b6e70aff903cde68")
                _movies.value = response.results
            } catch (e: Exception){
                Log.e("MovieViewModel", "Error: ${e.message}")
            }
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
        searchMovies(newQuery)
    }

    fun onSearchTextChanged(newText: String){
        _searchText.value = newText
    }

    fun onSearchTriggered() {
        searchMovies(_searchText.value)
    }

    private fun searchMovies(query: String){
        viewModelScope.launch {
            try {
                if (query.isNotBlank()) {
                    val response = repository.searchMovies(query, "3945b26c802bbcc0b6e70aff903cde68")
                    _movies.value = response.results
                } else {
                    getPopularMovies()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}