package com.fappslab.tmdbcompose.presentaion

import android.view.Window
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavHostController
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.core.navigation.DetailScreenNav
import com.fappslab.core.navigation.FavoriteNavigation
import com.fappslab.core.navigation.PopularNavigation
import com.fappslab.core.navigation.SearchNavigation
import com.fappslab.features.favorite.presentation.model.FavoriteNavItem
import com.fappslab.features.search.presentation.model.SearchNavItem
import com.fappslab.libraries.arch.extension.isNotNull
import com.fappslab.libraries.popular.presentation.model.PopularNavItem
import com.fappslab.tmdbcompose.presentaion.component.BottomBarView
import com.fappslab.tmdbcompose.presentaion.component.NavGraph
import com.fappslab.tmdbcompose.presentaion.component.currentRout

@Composable
fun MainScreen(
    window: Window? = null,
    color: Color = Color.Black,
    navController: NavHostController,
    popularNavigation: PopularNavigation,
    favoriteNavigation: FavoriteNavigation,
    searchNavigation: SearchNavigation,
    detailNavigation: DetailNavigation
) {
    if (window.isNotNull()) {
        LaunchedEffect(key1 = true) {
            window?.statusBarColor = color.toArgb()
        }
    }
    Scaffold(
        content = {
            Box(
                modifier = Modifier.padding(paddingValues = it)
            ) {
                NavGraph(
                    navController = navController,
                    popularNavigation = popularNavigation,
                    favoriteNavigation = favoriteNavigation,
                    searchNavigation = searchNavigation,
                    detailNavigation = detailNavigation
                )
            }
        },
        bottomBar = {
            if (currentRout(navController = navController) != DetailScreenNav.Detail.route) {
                BottomBarView(
                    navController = navController,
                    navItems = listOf(
                        PopularNavItem,
                        FavoriteNavItem,
                        SearchNavItem
                    )
                )
            }
        }
    )
}
