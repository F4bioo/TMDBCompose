package com.fappslab.features.favorite.data.repository

import com.fappslab.core.domain.model.Movie
import com.fappslab.core.domain.repository.FavoriteRepository
import com.fappslab.features.favorite.data.source.FavoriteLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val localDataSource: FavoriteLocalDataSource
) : FavoriteRepository {

    override fun getFavorites(): Flow<List<Movie>> {
        return localDataSource.getFavorites()
    }

    override suspend fun setFavorite(movie: Movie) {
        localDataSource.setFavorite(movie)
    }

    override suspend fun isFavorite(id: Int): Boolean {
        return localDataSource.isFavorite(id)
    }

    override suspend fun deleteFavorite(movie: Movie) {
        localDataSource.deleteFavorite(movie)
    }
}
