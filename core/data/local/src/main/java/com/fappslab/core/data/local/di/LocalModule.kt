package com.fappslab.core.data.local.di

import android.content.Context
import androidx.room.Room
import com.fappslab.core.data.local.dao.MovieDao
import com.fappslab.core.data.local.database.MovieDatabase
import com.fappslab.tmdbcompose.core.data.local.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object LocalModule {

    @Provides
    @Singleton
    fun providesMovieDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "${BuildConfig.DATA_BASE}.db"
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
