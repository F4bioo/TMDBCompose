package com.fappslab.tmdbcompose.features.popular.domain.repository

import androidx.paging.PagingData
import com.fappslab.tmdbcompose.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface PopularRepository {
    fun getMovies(): Flow<PagingData<Movie>>
}
