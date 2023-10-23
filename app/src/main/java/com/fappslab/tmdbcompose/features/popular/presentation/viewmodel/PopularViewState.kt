package com.fappslab.tmdbcompose.features.popular.presentation.viewmodel

import androidx.paging.PagingData
import com.fappslab.tmdbcompose.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class PopularViewState(
    val movies: Flow<PagingData<Movie>> = emptyFlow()
)
