package com.fappslab.tmdbcompose.features.search.presentation.viewmodel

import androidx.paging.compose.LazyPagingItems
import com.fappslab.tmdbcompose.core.domain.model.Movie

sealed class SearchViewAction {
    object HideKeyboard : SearchViewAction()
    data class ItemClicked(val id: Int) : SearchViewAction()
    data class TryAgain(val pagingItems: LazyPagingItems<Movie>) : SearchViewAction()
}
