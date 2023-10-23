package com.fappslab.tmdbcompose.core.di

import androidx.paging.PagingConfig
import com.fappslab.tmdbcompose.BuildConfig
import com.fappslab.tmdbcompose.core.data.api.MovieService
import com.fappslab.tmdbcompose.core.data.network.HttpClient
import com.fappslab.tmdbcompose.core.data.network.HttpClientImpl
import com.fappslab.tmdbcompose.core.data.network.QueryInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val READ_TIMEOUT = 15L
    private const val WRITE_TIMEOUT = 10L
    private const val CONNECT_TIMEOUT = 15L

    @Provides
    fun provideQueryInterceptor(): QueryInterceptor {
        return QueryInterceptor()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else HttpLoggingInterceptor.Level.NONE

        return HttpLoggingInterceptor().apply {
            setLevel(level)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        queryInterceptor: QueryInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(queryInterceptor)
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(true)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        factory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(factory)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        retrofit: Retrofit
    ): HttpClient {
        return HttpClientImpl(retrofit)
    }

    @Provides
    @Singleton
    fun provideService(
        httpClient: HttpClient
    ): MovieService {
        return httpClient.create(MovieService::class.java)
    }

    @Provides
    fun providesPagingConfig(): PagingConfig {
        return PagingConfig(pageSize = 20, initialLoadSize = 20)
    }

    @Provides
    @IO
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Main
    fun provideMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IO

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class Main
