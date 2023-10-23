package com.fappslab.tmdbcompose.features.detail.data.source.remote

import com.fappslab.tmdbcompose.core.data.model.MovieResponse
import com.fappslab.tmdbcompose.core.domain.model.Detail
import com.fappslab.tmdbcompose.features.detail.data.paging.SimilarMoviesPagingSource

interface DetailRemoteDataSource {
    suspend fun getMovieDetail(id: Int): Detail
    suspend fun getSimilarMovies(page: Int, id: Int): MovieResponse
    fun getSimilarMoviesPagingSource(id: Int): SimilarMoviesPagingSource
}
