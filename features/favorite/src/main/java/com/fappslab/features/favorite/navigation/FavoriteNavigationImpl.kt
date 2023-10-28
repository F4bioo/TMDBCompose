package com.fappslab.features.favorite.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.core.navigation.FavoriteNavigation
import com.fappslab.features.favorite.presentation.FavoriteScreen
import com.fappslab.features.favorite.presentation.model.FavoriteNavItem
import javax.inject.Inject

internal class FavoriteNavigationImpl @Inject constructor(
    private val detailNavigation: DetailNavigation
) : FavoriteNavigation {

    override fun navigateToFavorite(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.apply {
            composable(route = FavoriteNavItem.route) {
                FavoriteScreen(navController, detailNavigation)
            }
        }
    }
}
