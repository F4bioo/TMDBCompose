package com.fappslab.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface FavoriteNavigation {
    fun navigateToFavorite(navGraphBuilder: NavGraphBuilder, navController: NavHostController)
}
