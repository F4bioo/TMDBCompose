package com.fappslab.tmdbcompose.features.favorite.data.repository

import com.fappslab.tmdbcompose.core.domain.model.Movie
import com.fappslab.tmdbcompose.features.favorite.data.source.FavoriteLocalDataSource
import com.fappslab.tmdbcompose.features.favorite.domain.repository.FavoriteRepository
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
