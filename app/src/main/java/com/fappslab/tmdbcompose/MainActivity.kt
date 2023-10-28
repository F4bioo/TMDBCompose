package com.fappslab.tmdbcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.core.navigation.FavoriteNavigation
import com.fappslab.core.navigation.PopularNavigation
import com.fappslab.core.navigation.SearchNavigation
import com.fappslab.libraries.design.theme.TMDBComposeTheme
import com.fappslab.tmdbcompose.presentaion.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var popularNavigation: PopularNavigation

    @Inject
    lateinit var favoriteNavigation: FavoriteNavigation

    @Inject
    lateinit var searchNavigation: SearchNavigation

    @Inject
    lateinit var detailNavigation: DetailNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            TMDBComposeTheme {
                MainScreen(
                    navController = rememberNavController(),
                    popularNavigation = popularNavigation,
                    favoriteNavigation = favoriteNavigation,
                    searchNavigation = searchNavigation,
                    detailNavigation = detailNavigation
                )
            }
        }
    }
}
