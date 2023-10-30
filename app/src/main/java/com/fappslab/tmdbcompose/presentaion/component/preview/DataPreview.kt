package com.fappslab.tmdbcompose.presentaion.component.preview

import com.fappslab.features.favorite.presentation.model.FavoriteNavItem
import com.fappslab.features.popular.presentation.model.PopularNavItem
import com.fappslab.features.search.presentation.model.SearchNavItem
import com.fappslab.libraries.arch.route.NavRoute

fun navRoutesDataPreview(): List<NavRoute> {
    return listOf(
        PopularNavItem,
        FavoriteNavItem,
        SearchNavItem
    )
}
