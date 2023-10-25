package com.fappslab.tmdbcompose.features.favorite.data.source

import com.fappslab.tmdbcompose.core.data.common.extension.isNotNull
import com.fappslab.tmdbcompose.core.data.local.dao.MovieDao
import com.fappslab.tmdbcompose.core.data.local.model.extension.toMovieEntity
import com.fappslab.tmdbcompose.core.data.local.model.extension.toMovies
import com.fappslab.tmdbcompose.core.di.IO
import com.fappslab.tmdbcompose.core.domain.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteLocalDataSourceImpl @Inject constructor(
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
