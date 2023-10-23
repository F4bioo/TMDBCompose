package com.fappslab.tmdbcompose.features.popular.data.source.remote

import com.fappslab.tmdbcompose.core.data.model.MovieResponse
import com.fappslab.tmdbcompose.features.popular.data.paging.MoviesPagingSource

interface PopularRemoteDataSource {
    suspend fun getMovies(page: Int): MovieResponse
    fun getPopularMoviePagingSource(): MoviesPagingSource
}
