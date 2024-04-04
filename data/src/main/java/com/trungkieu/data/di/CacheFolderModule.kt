package com.trungkieu.data.di

import android.content.Context
import com.trungkieu.data.core.CacheFolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheFolderModule { // CacheFolderModule sẽ tạo ra dependency cho đối tượng sử dụng @CacheFolder

    @Provides
    @CacheFolder
    @Singleton
    fun provideCacheFolder(@ApplicationContext context: Context): File = context.cacheDir
}