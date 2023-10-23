package com.fappslab.tmdbcompose.features.search.data.source.remote

import com.fappslab.tmdbcompose.core.data.model.MovieResponse
import com.fappslab.tmdbcompose.features.search.data.paging.SearchMoviePagingSource

interface SearchRemoteDataSource {
    suspend fun getSearchMovie(page: Int, query: String): MovieResponse
    fun getSearchMoviePagingSource(query: String): SearchMoviePagingSource
}

