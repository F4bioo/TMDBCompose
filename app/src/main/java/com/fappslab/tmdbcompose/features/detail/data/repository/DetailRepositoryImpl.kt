package com.fappslab.tmdbcompose.features.detail.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fappslab.tmdbcompose.core.domain.model.Detail
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.features.detail.data.source.remote.DetailRemoteDataSource
import com.fappslab.tmdbcompose.features.detail.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val remoteDataSource: DetailRemoteDataSource,
    private val config: PagingConfig
) : DetailRepository {

    override suspend fun getMovieDetail(id: Int): Detail {
        return remoteDataSource.getMovieDetail(id)
    }

    override fun getSimilarMovies(id: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                remoteDataSource.getSimilarMoviesPagingSource(id)
            }
        ).flow
    }
}
