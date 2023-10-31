package com.fappslab.features.popular.data.source.remote

import com.fappslab.core.data.remote.model.MovieResponse
import com.fappslab.features.popular.data.paging.MoviesPagingSource

internal interface PopularRemoteDataSource {
    suspend fun getMovies(page: Int): MovieResponse
    fun getPopularMoviePagingSource(): MoviesPagingSource
}
