package com.fappslab.tmdbcompose.features.search.presentation.viewmodel

import androidx.paging.PagingData
import com.fappslab.tmdbcompose.core.data.model.extension.INIT_STRING
import com.fappslab.tmdbcompose.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchViewState(
    val query: String = INIT_STRING,
    val shouldShowLoading: Boolean = false,
    val movies: Flow<PagingData<Movie>> = emptyFlow()
)
