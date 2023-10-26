package com.fappslab.tmdbcompose.features.popular.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fappslab.tmdbcompose.core.data.common.constant.Util.FETCH_LIMIT
import com.fappslab.tmdbcompose.core.data.remote.model.extension.toMovies
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.features.popular.data.source.remote.PopularRemoteDataSource

class MoviesPagingSource(
    private val remoteDataSource: PopularRemoteDataSource
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return runCatching {
            val page = params.key ?: 1
            val response = remoteDataSource.getMovies(page)
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
