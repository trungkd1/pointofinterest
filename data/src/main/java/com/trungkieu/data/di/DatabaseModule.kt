package com.trungkieu.data.di

import android.content.Context
import androidx.room.Room
import com.trungkieu.data.database.PoiDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Dagger sử dụng annotation processor để quét code của bạn và tìm kiếm các annotation dạng @Module.
//Khi tìm thấy @Module, Dagger sẽ tạo ra một class (được gọi là module class) để quản lý các dependencies được định nghĩa trong module đó.
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PoiDatabase =
        Room.databaseBuilder(
            context,
            PoiDatabase::class.java,
            "trungkieu-poi-database"
        ).build()
}