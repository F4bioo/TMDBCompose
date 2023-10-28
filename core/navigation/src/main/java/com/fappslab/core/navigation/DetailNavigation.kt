package com.fappslab.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

const val DETAIL_ROUTE = "detail_screen"
const val DETAILS_ID_ARGS_KEY = "DETAILS_ID_ARGS_KEY"

interface DetailNavigation {
    fun navigateToDetail(navGraphBuilder: NavGraphBuilder, navController: NavHostController)
    fun navigateToDetail(navController: NavHostController, id: Int)
}

enum class DetailScreenNav(val route: String) {
    Detail(route = "$DETAIL_ROUTE/{${DETAILS_ID_ARGS_KEY}}")
}
