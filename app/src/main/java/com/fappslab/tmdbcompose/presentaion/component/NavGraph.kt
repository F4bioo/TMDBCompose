package com.fappslab.tmdbcompose.presentaion.component

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.core.navigation.FavoriteNavigation
import com.fappslab.core.navigation.PopularNavigation
import com.fappslab.core.navigation.SearchNavigation
import com.fappslab.libraries.popular.presentation.model.PopularNavItem

@Composable
fun NavGraph(
    navController: NavHostController,
    popularNavigation: PopularNavigation,
    favoriteNavigation: FavoriteNavigation,
    searchNavigation: SearchNavigation,
    detailNavigation: DetailNavigation
) {
    NavHost(
        navController = navController,
        startDestination = PopularNavItem.route,
    ) {
        popularNavigation.navigateToPopular(navGraphBuilder = this, navController)
        favoriteNavigation.navigateToFavorite(navGraphBuilder = this, navController)
        searchNavigation.navigateToSearch(navGraphBuilder = this, navController)
        detailNavigation.navigateToDetail(navGraphBuilder = this, navController)
    }
}
