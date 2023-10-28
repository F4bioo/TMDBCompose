package com.fappslab.libraries.arch.di

import com.fappslab.libraries.arch.annotation.IO
import com.fappslab.libraries.arch.annotation.Main
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal object ArchModule {

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
