package com.fappslab.features.search.presentation.viewmodel

import androidx.paging.PagingData
import com.fappslab.core.domain.Constant.INIT_STRING
import com.fappslab.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal data class SearchViewState(
    val query: String = INIT_STRING,
    val shouldShowLoading: Boolean = false,
    val movies: Flow<PagingData<Movie>> = emptyFlow()
)
