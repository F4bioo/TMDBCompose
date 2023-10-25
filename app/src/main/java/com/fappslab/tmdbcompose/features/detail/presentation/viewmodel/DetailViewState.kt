package com.fappslab.tmdbcompose.features.detail.presentation.viewmodel

import androidx.paging.PagingData
import com.fappslab.tmdbcompose.core.data.remote.model.extension.INIT_STRING
import com.fappslab.tmdbcompose.core.domain.model.Detail
import com.fappslab.tmdbcompose.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class DetailViewState(
    val detail: Detail = Detail(),
    val errorMessage: String = INIT_STRING,
    val shouldCollapseText: Boolean = true,
    val shouldShowLoading: Boolean = false,
    val isFavoriteChecked: Boolean = false,
    val movies: Flow<PagingData<Movie>> = emptyFlow()
)
