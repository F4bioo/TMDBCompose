package com.fappslab.features.popular.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.core.navigation.PopularNavigation
import com.fappslab.features.popular.presentation.PopularScreen
import com.fappslab.features.popular.presentation.model.PopularNavItem
import javax.inject.Inject

internal class PopularNavigationImpl @Inject constructor(
    private val detailNavigation: DetailNavigation
) : PopularNavigation {

    override fun navigateToPopular(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.apply {
            composable(route = PopularNavItem.route) {
                PopularScreen(navController, detailNavigation)
            }
        }
    }
}
