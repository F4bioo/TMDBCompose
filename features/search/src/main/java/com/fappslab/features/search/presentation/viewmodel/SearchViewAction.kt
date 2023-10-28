package com.fappslab.features.search.presentation.viewmodel

import androidx.paging.compose.LazyPagingItems
import com.fappslab.core.domain.model.Movie

internal sealed class SearchViewAction {
    object HideKeyboard : SearchViewAction()
    data class ItemClicked(val id: Int) : SearchViewAction()
    data class TryAgain(val pagingItems: LazyPagingItems<Movie>) : SearchViewAction()
}
