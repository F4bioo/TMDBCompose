package com.fappslab.tmdbcompose.features.detail.data.source.remote

import com.fappslab.tmdbcompose.core.data.remote.api.MovieService
import com.fappslab.tmdbcompose.core.data.remote.model.MovieResponse
import com.fappslab.tmdbcompose.core.data.remote.model.extension.toDetail
import com.fappslab.tmdbcompose.core.domain.model.Detail
import com.fappslab.tmdbcompose.features.detail.data.paging.SimilarMoviesPagingSource
import javax.inject.Inject

class DetailRemoteDataSourceImpl @Inject constructor(
    private val service: MovieService
) : DetailRemoteDataSource {

    override suspend fun getMovieDetail(id: Int): Detail {
        return service.getMovieDetail(id).toDetail()
    }

    override suspend fun getSimilarMovies(page: Int, id: Int): MovieResponse {
        return service.getSimilarMovies(page = page, id = id)
    }

    override fun getSimilarMoviesPagingSource(id: Int): SimilarMoviesPagingSource {
        return SimilarMoviesPagingSource(id, remoteDataSource = this)
    }
}
