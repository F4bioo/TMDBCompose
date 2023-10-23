package com.fappslab.tmdbcompose.features.search.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fappslab.tmdbcompose.core.data.constant.Common.FETCH_LIMIT
import com.fappslab.tmdbcompose.core.data.model.extension.toMovies
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.features.search.data.source.remote.SearchRemoteDataSource

class SearchMoviePagingSource(
    private val query: String,
    private val remoteDataSource: SearchRemoteDataSource
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return runCatching {
            val page = params.key ?: 1
            val response = remoteDataSource.getSearchMovie(page, query)
            val movies = response.result

            LoadResult.Page(
                data = movies.toMovies(),
                prevKey = if (page == 1) null else page.dec(),
                nextKey = if (movies.isNullOrEmpty()) null else page.inc()
            )

        }.getOrElse {
            LoadResult.Error(throwable = it)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(FETCH_LIMIT) ?: anchorPage?.nextKey?.minus(FETCH_LIMIT)
        }
    }
}
