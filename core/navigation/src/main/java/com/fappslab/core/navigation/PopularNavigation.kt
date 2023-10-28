package com.fappslab.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface PopularNavigation {
    fun navigateToPopular(navGraphBuilder: NavGraphBuilder, navController: NavHostController)
}
