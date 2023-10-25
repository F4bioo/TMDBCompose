package com.fappslab.tmdbcompose.core.presentaion.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.fappslab.tmdbcompose.R

const val ID_ARGS_KEY = "ID_ARGS_KEY"

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

    object Detail : BottomNavigationItem(
        titleRes = R.string.detail,
        icon = Icons.Default.Details,
        route = "detail_screen/{$ID_ARGS_KEY}"
    ) {
        fun withArgs(id: Int): String {
            return "detail_screen/$id"
        }
    }
}
