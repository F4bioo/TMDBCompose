package com.fappslab.tmdbcompose.features.favorite.domain.usecase

import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.features.favorite.domain.repository.FavoriteRepository
import javax.inject.Inject

class SetFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {

    suspend operator fun invoke(movie: Movie) {
        repository.setFavorite(movie)
    }
}
