package com.fappslab.tmdbcompose.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fappslab.tmdbcompose.core.data.local.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getFavorites(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavorite(movie: MovieEntity)

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getFavorite(id: Int): MovieEntity

    @Delete
    suspend fun deleteFavorite(movie: MovieEntity)
}
