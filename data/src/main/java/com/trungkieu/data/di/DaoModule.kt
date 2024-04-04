package com.trungkieu.data.di

import com.trungkieu.data.database.PoiDatabase
import com.trungkieu.data.features.categories.dao.CategoriesDao
import com.trungkieu.data.features.poi.dao.PoiDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
// DaoModule sẽ tạo ra một singleton và có thể dùng trưc tiếp ở class khác (kiểu static) : DaoModule.provideCategoriesDao
// Nhưng do sử dung @provideCategoriesDao và @providePoiDao nên sẽ được tự động chạy
object DaoModule {

    /**
     * Provide ctegories dao
     *
     * @param database : lấy giá trị từ hàm provideDatabase() của class DatabaseModule thông qua Dagger với @provides
     * @return : CategoriesDao được dùng cho class CategoriesLocalDataSource
     */
    @Provides
    @Singleton
    fun provideCtegoriesDao(database: PoiDatabase): CategoriesDao = database.categoriesDao()

    /**
     * Provide poi dao
     *
     * @param database : lấy giá trị từ hàm provideDatabase() của class DatabaseModule thông qua Dagger với @provides
     * @return
     */
    @Provides
    @Singleton
    fun providePoiDao(database: PoiDatabase): PoiDao = database.poiDao()
}