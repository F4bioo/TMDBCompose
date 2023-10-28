package com.fappslab.features.search.data.source.remote

import com.fappslab.core.data.remote.model.MovieResponse
import com.fappslab.features.search.data.api.SearchService
import com.fappslab.features.search.data.paging.SearchMoviePagingSource
import javax.inject.Inject

internal data class SearchRemoteDataSourceImpl @Inject constructor(
    private val service: SearchService
) : SearchRemoteDataSource {

    override suspend fun getSearchMovie(page: Int, query: String): MovieResponse {
        return service.getSearchMovie(page, query)
    }

    override fun getSearchMoviePagingSource(query: String): SearchMoviePagingSource {
        return SearchMoviePagingSource(query, remoteDataSource = this)
    }
}
