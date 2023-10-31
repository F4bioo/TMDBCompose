package com.fappslab.features.popular.presentation.viewmodel

import androidx.paging.PagingData
import com.fappslab.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal data class PopularViewState(
    val movies: Flow<PagingData<Movie>> = emptyFlow()
)
