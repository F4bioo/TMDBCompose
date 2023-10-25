package com.fappslab.tmdbcompose.features.detail.di

import androidx.paging.PagingConfig
import com.fappslab.tmdbcompose.core.data.remote.api.MovieService
import com.fappslab.tmdbcompose.features.detail.data.repository.DetailRepositoryImpl
import com.fappslab.tmdbcompose.features.detail.data.source.remote.DetailRemoteDataSource
import com.fappslab.tmdbcompose.features.detail.data.source.remote.DetailRemoteDataSourceImpl
import com.fappslab.tmdbcompose.features.detail.domain.repository.DetailRepository
import com.fappslab.tmdbcompose.features.detail.domain.usecase.GetMovieDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DetailModule {

    @Provides
    fun provideDetailRemoteDataSource(
        service: MovieService
    ): DetailRemoteDataSource {
        return DetailRemoteDataSourceImpl(service)
    }

    @Provides
    fun provideDetailRepository(
        remoteDataSource: DetailRemoteDataSource,
        config: PagingConfig
    ): DetailRepository {
        return DetailRepositoryImpl(remoteDataSource, config)
    }

    @Provides
    fun provideGetMovieDetailUseCase(
        repository: DetailRepository
    ): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(repository)
    }
}
