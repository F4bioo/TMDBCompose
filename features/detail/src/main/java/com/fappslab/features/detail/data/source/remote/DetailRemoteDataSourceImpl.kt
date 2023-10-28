package com.fappslab.features.detail.data.source.remote

import com.fappslab.core.data.remote.model.MovieResponse
import com.fappslab.core.domain.model.Detail
import com.fappslab.features.detail.data.api.DetailService
import com.fappslab.features.detail.data.model.extension.toDetail
import com.fappslab.features.detail.data.paging.SimilarMoviesPagingSource
import javax.inject.Inject

internal class DetailRemoteDataSourceImpl @Inject constructor(
    private val service: DetailService
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
