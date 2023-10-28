package com.fappslab.tmdbcompose.presentaion.component

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fappslab.libraries.arch.route.NavRoute
import com.fappslab.libraries.design.theme.black
import com.fappslab.libraries.design.theme.white
import com.fappslab.tmdbcompose.presentaion.component.preview.navRoutesDataPreview

@Composable
fun BottomBarView(
    navController: NavController,
    navItems: List<NavRoute>
) {

    BottomNavigation(
        contentColor = white,
        backgroundColor = black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navItems.forEach { destination ->
            BottomNavigationItem(
                selected = currentRoute == destination.route,
                onClick = {
                    navController.navigate(destination.route) {
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(imageVector = destination.icon, contentDescription = null)
                },
                label = {
                    Text(text = stringResource(id = destination.titleRes))
                }
            )
        }
    }
}

@Composable
fun currentRout(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Preview
@Composable
fun BottomBarViewPreview() {
    BottomBarView(
        navController = rememberNavController(),
        navItems = navRoutesDataPreview()
    )
}
