package com.fappslab.features.search.domain.repository

import androidx.paging.PagingData
import com.fappslab.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

internal interface SearchRepository {
    fun getSearchMovie(query: String): Flow<PagingData<Movie>>
}
