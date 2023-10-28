package com.fappslab.core.domain.usecase

import com.fappslab.core.domain.repository.FavoriteRepository
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {

    suspend operator fun invoke(id: Int): Boolean {
        return repository.isFavorite(id)
    }
}
