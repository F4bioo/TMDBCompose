package com.fappslab.tmdbcompose.features.detail.presentation.viewmodel

import androidx.paging.compose.LazyPagingItems
import com.fappslab.tmdbcompose.core.domain.model.Movie

sealed class DetailViewAction {
    data class ItemClicked(val id: Int) : DetailViewAction()
    data class TryAgain(val pagingItems: LazyPagingItems<Movie>) : DetailViewAction()
}
