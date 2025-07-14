package com.example.mobiledevelopmentproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobiledevelopmentproject.data.local.WatchlistManager
import com.example.mobiledevelopmentproject.ui.navigation.AppNavHost
import com.example.mobiledevelopmentproject.ui.navigation.BottomNavigationBar
import com.example.mobiledevelopmentproject.ui.screens.MovieListScreen
import com.example.mobiledevelopmentproject.ui.theme.MobileDevelopmentProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WatchlistManager.init((applicationContext))
        @OptIn(ExperimentalMaterial3Api::class)
        setContent {
            MobileDevelopmentProjectTheme {
                val navController = rememberNavController()
                val currentBackStackEntry = navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStackEntry.value?.destination
                val showBackButton = currentDestination?.route?.startsWith("movie_detail") == true

                Scaffold (
                    containerColor = Color(0xFF182945),
                    contentColor = Color(0xFFf8f7fc),
                    topBar = {
                        TopAppBar(
                            title = { Text("Movie 81", fontWeight = FontWeight.Bold) },
                            navigationIcon = {
                                if (showBackButton) {
                                    IconButton(onClick = {navController.popBackStack()}){
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = "Back",
                                            tint = Color(0xFFe3c100)
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color(0xFF060466),
                                titleContentColor = Color(0xFFe3c100)
                            )
                        )
                    },
                    bottomBar = {
                        BottomNavigationBar(navController)
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavHost(navController = navController)
                    }
                }
            }
        }
    }
}
