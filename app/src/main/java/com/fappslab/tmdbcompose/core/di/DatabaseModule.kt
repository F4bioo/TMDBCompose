package com.fappslab.tmdbcompose.core.di

import android.content.Context
import androidx.room.Room
import com.fappslab.tmdbcompose.core.data.common.constant.Util.DB_NAME
import com.fappslab.tmdbcompose.core.data.local.dao.MovieDao
import com.fappslab.tmdbcompose.core.data.local.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesMovieDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesMovieDao(
        database: MovieDatabase
    ): MovieDao {
        return database.movieDao()
    }
}
