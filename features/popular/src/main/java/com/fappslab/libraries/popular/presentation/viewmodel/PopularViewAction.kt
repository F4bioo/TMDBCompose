package com.fappslab.libraries.popular.presentation.viewmodel

import androidx.paging.compose.LazyPagingItems
import com.fappslab.core.domain.model.Movie

internal sealed class PopularViewAction {
    data class ItemClicked(val id: Int) : PopularViewAction()
    data class TryAgain(val pagingItems: LazyPagingItems<Movie>) : PopularViewAction()
}
