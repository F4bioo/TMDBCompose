package com.fappslab.tmdbcompose.features.detail.domain.repository

import androidx.paging.PagingData
import com.fappslab.tmdbcompose.core.domain.model.Detail
import com.fappslab.tmdbcompose.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    suspend fun getMovieDetail(id: Int): Detail
    fun getSimilarMovies(id: Int): Flow<PagingData<Movie>>
}
