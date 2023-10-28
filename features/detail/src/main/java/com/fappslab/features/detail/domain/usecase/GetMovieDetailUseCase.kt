package com.fappslab.features.detail.domain.usecase

import com.fappslab.features.detail.domain.model.Pack
import com.fappslab.features.detail.domain.repository.DetailRepository
import javax.inject.Inject

internal class GetMovieDetailUseCase @Inject constructor(
    private val repository: DetailRepository
) {

    suspend operator fun invoke(id: Int): Pack {
        val detail = repository.getMovieDetail(id)
        val movies = repository.getSimilarMovies(id)

        return Pack(detail, movies)
    }
}
