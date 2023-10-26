package com.fappslab.tmdbcompose.core.presentaion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fappslab.tmdbcompose.core.data.common.constant.Util.ID_ARGS_KEY
import com.fappslab.tmdbcompose.features.detail.navigation.DetailScreenNav
import com.fappslab.tmdbcompose.features.detail.presentation.DetailScreen
import com.fappslab.tmdbcompose.features.favorite.presentation.FavoriteScreen
import com.fappslab.tmdbcompose.features.popular.presentation.PopularScreen
import com.fappslab.tmdbcompose.features.search.presentation.SearchScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationItem.Popular.route
    ) {
        composable(
            route = BottomNavigationItem.Popular.route
        ) {
            PopularScreen(navController)
        }
        composable(
            route = BottomNavigationItem.Search.route
        ) {
            SearchScreen(navController)
        }
        composable(
            route = BottomNavigationItem.Favorite.route
        ) {
            FavoriteScreen(navController)
        }
        composable(
            route = DetailScreenNav.Detail.route,
            arguments = listOf(
                navArgument(ID_ARGS_KEY) {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            DetailScreen(navController)
        }
    }
}
