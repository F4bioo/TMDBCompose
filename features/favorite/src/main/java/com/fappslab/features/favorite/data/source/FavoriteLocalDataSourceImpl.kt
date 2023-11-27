package com.fappslab.features.favorite.data.source

import com.fappslab.core.data.local.dao.MovieDao
import com.fappslab.core.data.local.model.extension.toMovieEntity
import com.fappslab.core.data.local.model.extension.toMovies
import com.fappslab.core.domain.model.Movie
import com.fappslab.libraries.arch.annotation.IO
import com.fappslab.libraries.arch.extension.isNotNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class FavoriteLocalDataSourceImpl @Inject constructor(
    private val dao: MovieDao,
    @IO private val dispatcher: CoroutineDispatcher
) : FavoriteLocalDataSource {

    override fun getFavorites(): Flow<List<Movie>> {
        return dao.getFavorites().map { it.toMovies() }
            .flowOn(dispatcher)
    }

    override suspend fun setFavorite(movie: Movie) {
        withContext(dispatcher) {
            dao.setFavorite(movie.toMovieEntity())
        }
    }

    override suspend fun isFavorite(id: Int): Boolean {
        return withContext(dispatcher) {
            dao.getFavorite(id).isNotNull()
        }
    }

    override suspend fun deleteFavorite(movie: Movie) {
        withContext(dispatcher) {
            dao.deleteFavorite(movie.toMovieEntity())
        }
    }
}
