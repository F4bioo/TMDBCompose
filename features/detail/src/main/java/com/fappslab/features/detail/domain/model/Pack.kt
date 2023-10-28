package com.fappslab.features.detail.domain.model

import androidx.paging.PagingData
import com.fappslab.core.domain.model.Detail
import com.fappslab.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

data class Pack(
    val detail: Detail,
    val movies: Flow<PagingData<Movie>>
)
