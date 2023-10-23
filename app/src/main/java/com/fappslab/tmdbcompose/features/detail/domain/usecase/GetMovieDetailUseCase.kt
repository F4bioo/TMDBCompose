package com.fappslab.tmdbcompose.features.detail.domain.usecase

import com.fappslab.tmdbcompose.features.detail.domain.model.Pack
import com.fappslab.tmdbcompose.features.detail.domain.repository.DetailRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: DetailRepository
) {

    suspend operator fun invoke(id: Int): Pack {
        val detail = repository.getMovieDetail(id)
        val movies = repository.getSimilarMovies(id)

        return Pack(detail, movies)
    }
}
