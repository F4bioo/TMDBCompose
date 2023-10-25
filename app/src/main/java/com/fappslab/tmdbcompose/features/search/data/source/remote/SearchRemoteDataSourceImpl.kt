package com.fappslab.tmdbcompose.features.search.data.source.remote

import com.fappslab.tmdbcompose.core.data.remote.api.MovieService
import com.fappslab.tmdbcompose.core.data.remote.model.MovieResponse
import com.fappslab.tmdbcompose.features.search.data.paging.SearchMoviePagingSource
import javax.inject.Inject

data class SearchRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
) : SearchRemoteDataSource {

    override suspend fun getSearchMovie(page: Int, query: String): MovieResponse {
        return service.getSearchMovie(page, query)
    }

    override fun getSearchMoviePagingSource(query: String): SearchMoviePagingSource {
        return SearchMoviePagingSource(query, remoteDataSource = this)
    }
}
