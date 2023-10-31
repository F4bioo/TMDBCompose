package com.fappslab.features.popular.data.source.remote

import com.fappslab.core.data.remote.model.MovieResponse
import com.fappslab.features.popular.data.api.PopularService
import com.fappslab.features.popular.data.paging.MoviesPagingSource
import javax.inject.Inject

internal class PopularRemoteDataSourceImpl @Inject constructor(
    private val service: PopularService
) : PopularRemoteDataSource {

    override suspend fun getMovies(page: Int): MovieResponse =
        service.getMovies(page)

    override fun getPopularMoviePagingSource(): MoviesPagingSource {
        return MoviesPagingSource(remoteDataSource = this)
    }
}
