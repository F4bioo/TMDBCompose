package com.fappslab.features.detail.domain.repository

import androidx.paging.PagingData
import com.fappslab.core.domain.model.Detail
import com.fappslab.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

internal interface DetailRepository {
    suspend fun getMovieDetail(id: Int): Detail
    fun getSimilarMovies(id: Int): Flow<PagingData<Movie>>
}
