package com.fappslab.tmdbcompose.features.favorite.domain.usecase

import com.fappslab.tmdbcompose.features.favorite.domain.repository.FavoriteRepository
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {

    suspend operator fun invoke(id: Int): Boolean {
        return repository.isFavorite(id)
    }
}
