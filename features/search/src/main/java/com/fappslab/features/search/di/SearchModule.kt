package com.fappslab.features.search.di

import androidx.paging.PagingConfig
import com.fappslab.core.data.remote.network.HttpClient
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.core.navigation.SearchNavigation
import com.fappslab.features.search.data.api.SearchService
import com.fappslab.features.search.data.repository.SearchRepositoryImpl
import com.fappslab.features.search.data.source.remote.SearchRemoteDataSource
import com.fappslab.features.search.data.source.remote.SearchRemoteDataSourceImpl
import com.fappslab.features.search.domain.repository.SearchRepository
import com.fappslab.features.search.domain.usecase.GetSearchMovieUseCase
import com.fappslab.features.search.navigation.SearchNavigationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object SearchModule {

    @Provides
    @Singleton
    fun provideService(
        httpClient: HttpClient
    ): SearchService {
        return httpClient.create(SearchService::class.java)
    }

    @Provides
    fun provideSearchRemoteDataSource(
        service: SearchService
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

    @Provides
    fun provideSearchNavigation(
        detailNavigation: DetailNavigation
    ): SearchNavigation {
        return SearchNavigationImpl(detailNavigation)
    }
}
