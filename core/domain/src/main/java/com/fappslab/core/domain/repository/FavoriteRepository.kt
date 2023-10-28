package com.fappslab.core.domain.repository

import com.fappslab.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(): Flow<List<Movie>>
    suspend fun setFavorite(movie: Movie)
    suspend fun isFavorite(id: Int): Boolean
    suspend fun deleteFavorite(movie: Movie)
}
