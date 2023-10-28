package com.fappslab.features.detail.presentation.viewmodel

import androidx.paging.compose.LazyPagingItems
import com.fappslab.core.domain.model.Movie

internal sealed class DetailViewAction {
    data class ItemClicked(val id: Int) : DetailViewAction()
    data class TryAgain(val pagingItems: LazyPagingItems<Movie>) : DetailViewAction()
}
