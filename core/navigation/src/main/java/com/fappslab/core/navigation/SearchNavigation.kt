package com.fappslab.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface SearchNavigation {
    fun navigateToSearch(navGraphBuilder: NavGraphBuilder, navController: NavHostController)
}
