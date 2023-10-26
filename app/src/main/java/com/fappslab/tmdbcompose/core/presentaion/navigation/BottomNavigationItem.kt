package com.fappslab.tmdbcompose.core.presentaion.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.fappslab.tmdbcompose.R

sealed class BottomNavigationItem(
    @StringRes val titleRes: Int,
    val icon: ImageVector,
    val route: String
) {
    object Popular : BottomNavigationItem(
        titleRes = R.string.popular,
        icon = Icons.Default.Movie,
        route = "popular_screen"
    )

    object Favorite : BottomNavigationItem(
        titleRes = R.string.favorite,
        icon = Icons.Default.Favorite,
        route = "favorite_screen"
    )

    object Search : BottomNavigationItem(
        titleRes = R.string.search,
        icon = Icons.Default.Search,
        route = "search_screen"
    )
}
