package com.fappslab.tmdbcompose.features.favorite.domain.usecase

import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.features.favorite.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {

    operator fun invoke(): Flow<List<Movie>> {
        return repository.getFavorites()
    }
}
