package com.fappslab.tmdbcompose.features.search.domain.repository

import androidx.paging.PagingData
import com.fappslab.tmdbcompose.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getSearchMovie(query: String): Flow<PagingData<Movie>>
}
