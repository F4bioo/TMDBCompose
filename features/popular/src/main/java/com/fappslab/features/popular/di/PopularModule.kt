package com.fappslab.features.popular.di

import androidx.paging.PagingConfig
import com.fappslab.core.data.remote.network.HttpClient
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.core.navigation.PopularNavigation
import com.fappslab.features.popular.data.api.PopularService
import com.fappslab.features.popular.data.repository.PopularRepositoryImpl
import com.fappslab.features.popular.data.source.remote.PopularRemoteDataSource
import com.fappslab.features.popular.data.source.remote.PopularRemoteDataSourceImpl
import com.fappslab.features.popular.domain.repository.PopularRepository
import com.fappslab.features.popular.domain.usecase.GetMoviesUseCase
import com.fappslab.features.popular.navigation.PopularNavigationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object PopularModule {

    @Provides
    @Singleton
    fun provideService(
        httpClient: HttpClient
    ): PopularService {
        return httpClient.create(PopularService::class.java)
    }

    @Provides
    fun providePopularRemoteDataSource(
        service: PopularService
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

    @Provides
    fun providePopularNavigation(
        detailNavigation: DetailNavigation
    ): PopularNavigation {
        return PopularNavigationImpl(detailNavigation)
    }
}
