package com.fappslab.features.detail.data.source.remote

import com.fappslab.core.data.remote.model.MovieResponse
import com.fappslab.core.domain.model.Detail
import com.fappslab.features.detail.data.paging.SimilarMoviesPagingSource

internal interface DetailRemoteDataSource {
    suspend fun getMovieDetail(id: Int): Detail
    suspend fun getSimilarMovies(page: Int, id: Int): MovieResponse
    fun getSimilarMoviesPagingSource(id: Int): SimilarMoviesPagingSource
}
