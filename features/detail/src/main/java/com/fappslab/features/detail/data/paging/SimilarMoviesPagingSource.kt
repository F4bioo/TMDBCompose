package com.fappslab.features.detail.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fappslab.core.data.remote.model.extension.toMovies
import com.fappslab.core.domain.Constant.FETCH_LIMIT
import com.fappslab.core.domain.model.Movie
import com.fappslab.features.detail.data.source.remote.DetailRemoteDataSource

internal class SimilarMoviesPagingSource(
    private val id: Int,
    private val remoteDataSource: DetailRemoteDataSource
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return runCatching {
            val page = params.key ?: 1
            val response = remoteDataSource.getSimilarMovies(page, id)
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
