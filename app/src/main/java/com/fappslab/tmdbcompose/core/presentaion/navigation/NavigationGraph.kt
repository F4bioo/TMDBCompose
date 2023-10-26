package com.fappslab.tmdbcompose.core.presentaion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fappslab.tmdbcompose.core.data.common.constant.Util.ID_ARGS_KEY
import com.fappslab.tmdbcompose.features.detail.navigation.DetailScreenNav
import com.fappslab.tmdbcompose.features.detail.presentation.Detail
import com.fappslab.tmdbcompose.features.favorite.presentation.Favorite
import com.fappslab.tmdbcompose.features.popular.presentation.Popular
import com.fappslab.tmdbcompose.features.search.presentation.Search

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationItem.Popular.route
    ) {
        composable(
            route = BottomNavigationItem.Popular.route
        ) {
            Popular(navController)
        }
        composable(
            route = BottomNavigationItem.Search.route
        ) {
            Search(navController)
        }
        composable(
            route = BottomNavigationItem.Favorite.route
        ) {
            Favorite(navController)
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
            Detail(navController)
        }
    }
}
