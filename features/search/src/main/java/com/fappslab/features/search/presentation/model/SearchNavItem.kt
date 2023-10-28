package com.fappslab.features.search.presentation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.fappslab.libraries.arch.route.NavRoute
import com.fappslab.tmdbcompose.features.search.R

object SearchNavItem : NavRoute {
    override val titleRes: Int = R.string.search
    override val icon: ImageVector = Icons.Default.Search
    override val route: String = "search_screen"
}
