package com.example.mobiledevelopmentproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobiledevelopmentproject.ui.screens.HomeScreen
import com.example.mobiledevelopmentproject.ui.screens.MovieDetailScreen
import com.example.mobiledevelopmentproject.ui.screens.MovieListScreen
import com.example.mobiledevelopmentproject.ui.screens.WatchlistScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route){
        composable(BottomNavItem.Home.route){
            HomeScreen(navController = navController)
        }
        composable(BottomNavItem.Search.route){
            MovieListScreen(navController = navController)
        }
        composable(BottomNavItem.Watchlist.route){
            WatchlistScreen(navController = navController)
        }
        composable("movie_detail/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
            movieId?.let{
                MovieDetailScreen(movieId = it)
            }
        }
    }
}