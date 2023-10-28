package com.fappslab.features.favorite.di

import com.fappslab.core.data.local.dao.MovieDao
import com.fappslab.core.domain.repository.FavoriteRepository
import com.fappslab.core.domain.usecase.DeleteFavoriteUseCase
import com.fappslab.core.domain.usecase.IsFavoriteUseCase
import com.fappslab.core.domain.usecase.SetFavoriteUseCase
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.core.navigation.FavoriteNavigation
import com.fappslab.features.favorite.data.repository.FavoriteRepositoryImpl
import com.fappslab.features.favorite.data.source.FavoriteLocalDataSource
import com.fappslab.features.favorite.data.source.FavoriteLocalDataSourceImpl
import com.fappslab.features.favorite.domain.GetFavoritesUseCase
import com.fappslab.features.favorite.navigation.FavoriteNavigationImpl
import com.fappslab.libraries.arch.annotation.IO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
internal object FavoriteModule {

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

    @Provides
    fun provideFavoriteNavigation(
        detailNavigation: DetailNavigation
    ): FavoriteNavigation {
        return FavoriteNavigationImpl(detailNavigation)
    }
}
