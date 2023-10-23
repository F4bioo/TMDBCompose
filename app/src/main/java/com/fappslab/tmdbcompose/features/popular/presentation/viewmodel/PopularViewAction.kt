package com.fappslab.tmdbcompose.features.popular.presentation.viewmodel

import androidx.paging.compose.LazyPagingItems
import com.fappslab.tmdbcompose.core.domain.model.Movie

sealed class PopularViewAction {
    data class ItemClicked(val id: Int) : PopularViewAction()
    data class TryAgain(val pagingItems: LazyPagingItems<Movie>) : PopularViewAction()
}
