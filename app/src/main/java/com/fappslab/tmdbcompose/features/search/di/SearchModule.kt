package com.fappslab.tmdbcompose.features.search.di

import androidx.paging.PagingConfig
import com.fappslab.tmdbcompose.core.data.api.MovieService
import com.fappslab.tmdbcompose.features.search.data.repository.SearchRepositoryImpl
import com.fappslab.tmdbcompose.features.search.data.source.remote.SearchRemoteDataSource
import com.fappslab.tmdbcompose.features.search.data.source.remote.SearchRemoteDataSourceImpl
import com.fappslab.tmdbcompose.features.search.domain.repository.SearchRepository
import com.fappslab.tmdbcompose.features.search.domain.usecase.GetSearchMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    fun provideSearchRemoteDataSource(
        service: MovieService
    ): SearchRemoteDataSource {
        return SearchRemoteDataSourceImpl(service)
    }

    @Provides
    fun provideSearchRepository(
        remoteDataSource: SearchRemoteDataSource,
        config: PagingConfig
    ): SearchRepository {
        return SearchRepositoryImpl(remoteDataSource, config)
    }

    @Provides
    fun provideGetSearchMovieUseCase(
        repository: SearchRepository
    ): GetSearchMovieUseCase {
        return GetSearchMovieUseCase(repository)
    }
}
