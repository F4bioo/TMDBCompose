package com.fappslab.features.detail.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fappslab.core.navigation.DETAILS_ID_ARGS_KEY
import com.fappslab.core.navigation.DETAIL_ROUTE
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.core.navigation.DetailScreenNav
import com.fappslab.features.detail.presentation.DetailScreen
import javax.inject.Inject

internal class DetailNavigationImpl @Inject constructor() : DetailNavigation {

    override fun navigateToDetail(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(
            route = DetailScreenNav.Detail.route,
            arguments = listOf(
                navArgument(DETAILS_ID_ARGS_KEY) {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {
            DetailScreen(navController, detailNavigation = this@DetailNavigationImpl)
        }
    }

    override fun navigateToDetail(navController: NavHostController, id: Int) {
        navController.navigate(route = "$DETAIL_ROUTE/$id")
    }
}
