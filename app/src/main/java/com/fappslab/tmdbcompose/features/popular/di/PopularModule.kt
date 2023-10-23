package com.fappslab.tmdbcompose.features.popular.di

import androidx.paging.PagingConfig
import com.fappslab.tmdbcompose.core.data.api.MovieService
import com.fappslab.tmdbcompose.features.popular.data.repository.PopularRepositoryImpl
import com.fappslab.tmdbcompose.features.popular.data.source.remote.PopularRemoteDataSource
import com.fappslab.tmdbcompose.features.popular.data.source.remote.PopularRemoteDataSourceImpl
import com.fappslab.tmdbcompose.features.popular.domain.repository.PopularRepository
import com.fappslab.tmdbcompose.features.popular.domain.usecase.GetMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PopularModule {

    @Provides
    fun providePopularRemoteDataSource(
        service: MovieService
    ): PopularRemoteDataSource {
        return PopularRemoteDataSourceImpl(service)
    }

    @Provides
    fun providePopularRepository(
        remoteDataSource: PopularRemoteDataSource,
        config: PagingConfig
    ): PopularRepository {
        return PopularRepositoryImpl(remoteDataSource, config)
    }

    @Provides
    fun provideGetMoviesUseCase(
        repository: PopularRepository
    ): GetMoviesUseCase {
        return GetMoviesUseCase(repository)
    }
}
