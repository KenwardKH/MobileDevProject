package com.example.mobiledevelopmentproject.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.mobiledevelopmentproject.data.model.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object WatchlistManager {
    private const val PREF_NAME = "watchlist_pref"
    private const val KEY_WATCHLIST = "watchlist"

    private lateinit var prefs: SharedPreferences
    private val gson = Gson()

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun addMovie(movie: Movie) {
        val list = getWatchlist().toMutableList()
        if (list.none {it.id == movie.id}) {
            list.add(movie)
            saveWatchlist(list)
        }
    }

    fun removeMovie(movieId: Int){
        val list = getWatchlist().filter { it.id != movieId}
        saveWatchlist(list)
    }

    fun getWatchlist(): List<Movie> {
        val json = prefs.getString(KEY_WATCHLIST, null)
        return if (!json.isNullOrEmpty()) {
            val type = object  : TypeToken<List<Movie>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun isInWatchList(movieId: Int): Boolean {
        return getWatchlist().any { it.id == movieId }
    }

    fun toggleMovie (movie: Movie) {
        if (isInWatchList(movie.id)) {
            removeMovie(movie.id)
        } else {
            addMovie(movie)
        }
    }

    private fun saveWatchlist(list: List<Movie>) {
        val editor = prefs.edit()
        editor.putString(KEY_WATCHLIST, gson.toJson(list))
        editor.apply()
    }
}