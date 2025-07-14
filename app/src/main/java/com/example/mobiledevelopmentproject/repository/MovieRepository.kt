package com.example.mobiledevelopmentproject.repository

import androidx.compose.ui.input.key.Key
import com.example.mobiledevelopmentproject.data.model.MovieResponse
import com.example.mobiledevelopmentproject.data.remote.RetrofitInstance
import kotlin.coroutines.CoroutineContext

class MovieRepository {
    suspend fun getPopularMovies(apiKey: String) =
        RetrofitInstance.api.getPopularMovies(apiKey)
    suspend fun getMovieDetail(movieId: Int, apiKey: String) =
        RetrofitInstance.api.getMovieDetail(movieId, apiKey)
    suspend fun searchMovies(query: String, apiKey: String): MovieResponse {
        return RetrofitInstance.api.searchMovies(apiKey, query)
    }
    suspend fun getNowPlaying(apiKey: String): MovieResponse =
        RetrofitInstance.api.getNowPlaying(apiKey)
    suspend fun getTopRated(apiKey: String): MovieResponse =
        RetrofitInstance.api.getTopRated(apiKey)
}