package com.fappslab.tmdbcompose.features.search.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.features.search.data.source.remote.SearchRemoteDataSource
import com.fappslab.tmdbcompose.features.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: SearchRemoteDataSource,
    private val config: PagingConfig
) : SearchRepository {

    override fun getSearchMovie(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                remoteDataSource.getSearchMoviePagingSource(query)
            }
        ).flow
    }
}
