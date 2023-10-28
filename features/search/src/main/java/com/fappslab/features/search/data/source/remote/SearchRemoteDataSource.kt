package com.fappslab.features.search.data.source.remote

import com.fappslab.core.data.remote.model.MovieResponse
import com.fappslab.features.search.data.paging.SearchMoviePagingSource

internal interface SearchRemoteDataSource {
    suspend fun getSearchMovie(page: Int, query: String): MovieResponse
    fun getSearchMoviePagingSource(query: String): SearchMoviePagingSource
}

