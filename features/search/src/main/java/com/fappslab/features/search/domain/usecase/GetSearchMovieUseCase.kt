package com.fappslab.features.search.domain.usecase

import androidx.paging.PagingData
import com.fappslab.core.domain.model.Movie
import com.fappslab.features.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetSearchMovieUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    operator fun invoke(query: String): Flow<PagingData<Movie>> =
        repository.getSearchMovie(query)
}
