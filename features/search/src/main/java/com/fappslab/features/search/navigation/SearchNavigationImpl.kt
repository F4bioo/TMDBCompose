package com.fappslab.features.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.core.navigation.SearchNavigation
import com.fappslab.features.search.presentation.SearchScreen
import com.fappslab.features.search.presentation.model.SearchNavItem
import javax.inject.Inject

internal class SearchNavigationImpl @Inject constructor(
    private val detailNavigation: DetailNavigation
) : SearchNavigation {

    override fun navigateToSearch(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.apply {
            composable(route = SearchNavItem.route) {
                SearchScreen(navController, detailNavigation)
            }
        }
    }
}
