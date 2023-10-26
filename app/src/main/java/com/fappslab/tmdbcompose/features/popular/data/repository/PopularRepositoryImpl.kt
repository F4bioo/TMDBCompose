package com.fappslab.tmdbcompose.features.popular.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.features.popular.data.source.remote.PopularRemoteDataSource
import com.fappslab.tmdbcompose.features.popular.domain.repository.PopularRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PopularRepositoryImpl @Inject constructor(
    private val remoteDataSource: PopularRemoteDataSource,
    private val config: PagingConfig
) : PopularRepository {

    override fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = config,
            pagingSourceFactory = remoteDataSource::getPopularMoviePagingSource
        ).flow
    }
}
