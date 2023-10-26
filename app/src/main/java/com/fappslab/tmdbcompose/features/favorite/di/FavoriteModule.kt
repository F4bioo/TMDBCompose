package com.fappslab.tmdbcompose.features.favorite.di

import com.fappslab.tmdbcompose.core.data.local.dao.MovieDao
import com.fappslab.tmdbcompose.core.di.IO
import com.fappslab.tmdbcompose.features.favorite.data.repository.FavoriteRepositoryImpl
import com.fappslab.tmdbcompose.features.favorite.data.source.FavoriteLocalDataSource
import com.fappslab.tmdbcompose.features.favorite.data.source.FavoriteLocalDataSourceImpl
import com.fappslab.tmdbcompose.features.favorite.domain.repository.FavoriteRepository
import com.fappslab.tmdbcompose.features.favorite.domain.usecase.DeleteFavoriteUseCase
import com.fappslab.tmdbcompose.features.favorite.domain.usecase.GetFavoritesUseCase
import com.fappslab.tmdbcompose.features.favorite.domain.usecase.IsFavoriteUseCase
import com.fappslab.tmdbcompose.features.favorite.domain.usecase.SetFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object FavoriteModule {

    @Provides
    fun provideFavoriteLocalDataSource(
        dao: MovieDao,
        @IO dispatcher: CoroutineDispatcher
    ): FavoriteLocalDataSource {
        return FavoriteLocalDataSourceImpl(dao, dispatcher)
    }

    @Provides
    fun provideFavoriteRepository(
        localDataSource: FavoriteLocalDataSource
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(localDataSource)
    }

    @Provides
    fun provideDeleteMovieUseCase(
        repository: FavoriteRepository
    ): DeleteFavoriteUseCase {
        return DeleteFavoriteUseCase(repository)
    }

    @Provides
    fun provideGetMoviesUseCase(
        repository: FavoriteRepository
    ): GetFavoritesUseCase {
        return GetFavoritesUseCase(repository)
    }

    @Provides
    fun provideIsFavoriteUseCase(
        repository: FavoriteRepository
    ): IsFavoriteUseCase {
        return IsFavoriteUseCase(repository)
    }

    @Provides
    fun provideSetMovieUseCase(
        repository: FavoriteRepository
    ): SetFavoriteUseCase {
        return SetFavoriteUseCase(repository)
    }
}
