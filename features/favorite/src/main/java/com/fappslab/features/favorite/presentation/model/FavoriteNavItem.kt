package com.fappslab.features.favorite.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import com.fappslab.libraries.arch.route.NavRoute
import com.fappslab.tmdbcompose.features.favorites.R

object FavoriteNavItem : NavRoute {
    override val titleRes: Int = R.string.favorite
    override val icon: ImageVector = Icons.Default.Favorite
    override val route: String = "favorite_screen"
}
