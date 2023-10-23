package com.fappslab.tmdbcompose.features.popular.data.source.remote

import com.fappslab.tmdbcompose.core.data.api.MovieService
import com.fappslab.tmdbcompose.core.data.model.MovieResponse
import com.fappslab.tmdbcompose.features.popular.data.paging.MoviesPagingSource
import javax.inject.Inject

class PopularRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
) : PopularRemoteDataSource {

    override suspend fun getMovies(page: Int): MovieResponse =
        service.getMovies(page)

    override fun getPopularMoviePagingSource(): MoviesPagingSource {
        return MoviesPagingSource(remoteDataSource = this)
    }
}
