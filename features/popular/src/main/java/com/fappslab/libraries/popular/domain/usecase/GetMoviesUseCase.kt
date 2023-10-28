package com.fappslab.libraries.popular.domain.usecase

import androidx.paging.PagingData
import com.fappslab.core.domain.model.Movie
import com.fappslab.libraries.popular.domain.repository.PopularRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetMoviesUseCase @Inject constructor(
    private val repository: PopularRepository
) {

    operator fun invoke(): Flow<PagingData<Movie>> =
        repository.getMovies()
}
