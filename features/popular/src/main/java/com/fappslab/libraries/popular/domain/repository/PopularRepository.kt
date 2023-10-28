package com.fappslab.libraries.popular.domain.repository

import androidx.paging.PagingData
import com.fappslab.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

internal interface PopularRepository {
    fun getMovies(): Flow<PagingData<Movie>>
}
