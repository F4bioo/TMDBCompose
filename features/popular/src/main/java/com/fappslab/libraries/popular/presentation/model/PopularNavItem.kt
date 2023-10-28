package com.fappslab.libraries.popular.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.ui.graphics.vector.ImageVector
import com.fappslab.libraries.arch.route.NavRoute
import com.fappslab.tmdbcompose.features.popular.R

object PopularNavItem : NavRoute {
    override val titleRes: Int = R.string.popular
    override val icon: ImageVector = Icons.Default.Movie
    override val route: String = "popular_screen"
}
