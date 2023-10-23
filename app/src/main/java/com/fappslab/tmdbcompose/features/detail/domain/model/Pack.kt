package com.fappslab.tmdbcompose.features.detail.domain.model

import androidx.paging.PagingData
import com.fappslab.tmdbcompose.core.domain.model.Detail
import com.fappslab.tmdbcompose.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

data class Pack(
    val detail: Detail,
    val movies: Flow<PagingData<Movie>>
)
