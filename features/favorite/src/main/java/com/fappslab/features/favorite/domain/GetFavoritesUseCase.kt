package com.fappslab.features.favorite.domain

import com.fappslab.core.domain.model.Movie
import com.fappslab.core.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {

    operator fun invoke(): Flow<List<Movie>> {
        return repository.getFavorites()
    }
}
