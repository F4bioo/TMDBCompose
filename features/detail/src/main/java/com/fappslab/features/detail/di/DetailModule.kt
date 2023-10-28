package com.fappslab.features.detail.di

import androidx.paging.PagingConfig
import com.fappslab.core.data.remote.network.HttpClient
import com.fappslab.core.navigation.DetailNavigation
import com.fappslab.features.detail.data.api.DetailService
import com.fappslab.features.detail.data.repository.DetailRepositoryImpl
import com.fappslab.features.detail.data.source.remote.DetailRemoteDataSource
import com.fappslab.features.detail.data.source.remote.DetailRemoteDataSourceImpl
import com.fappslab.features.detail.domain.repository.DetailRepository
import com.fappslab.features.detail.domain.usecase.GetMovieDetailUseCase
import com.fappslab.features.detail.navigation.DetailNavigationImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DetailModule {

    @Provides
    @Singleton
    fun provideService(
        httpClient: HttpClient
    ): DetailService {
        return httpClient.create(DetailService::class.java)
    }

    @Provides
    fun provideDetailRemoteDataSource(
        service: DetailService
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

    @Provides
    fun provideDetailNavigation(): DetailNavigation {
        return DetailNavigationImpl()
    }
}
