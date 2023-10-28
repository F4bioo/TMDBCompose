package com.fappslab.core.domain.usecase

import com.fappslab.core.domain.model.Movie
import com.fappslab.core.domain.repository.FavoriteRepository
import javax.inject.Inject

class SetFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {

    suspend operator fun invoke(movie: Movie) {
        repository.setFavorite(movie)
    }
}
