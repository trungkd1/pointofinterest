package com.trungkieu.data.di

import com.trungkieu.data.core.UseCaseDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Singleton
    @UseCaseDispatcher
    fun provideIoDispatcher() = Dispatchers.IO
}