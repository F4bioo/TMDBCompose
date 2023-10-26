package com.fappslab.tmdbcompose.features.popular.domain.usecase

import androidx.paging.PagingData
import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.features.popular.domain.repository.PopularRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: PopularRepository
) {

    operator fun invoke(): Flow<PagingData<Movie>> =
        repository.getMovies()
}
