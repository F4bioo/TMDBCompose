package com.fappslab.tmdbcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.fappslab.tmdbcompose.core.presentaion.MainScreen
import com.fappslab.tmdbcompose.ui.theme.TMDBComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TMDBComposeTheme {
                MainScreen(navController = rememberNavController())
            }
        }
    }
}
