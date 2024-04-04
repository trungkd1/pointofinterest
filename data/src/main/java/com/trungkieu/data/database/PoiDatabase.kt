package com.trungkieu.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.trungkieu.data.features.categories.dao.CategoriesDao
import com.trungkieu.data.features.categories.model.CategoryEntity
import com.trungkieu.data.features.poi.dao.PoiDao
import com.trungkieu.data.features.poi.model.PoiCommentEntity
import com.trungkieu.data.features.poi.model.PoiEntity
import com.trungkieu.data.features.poi.model.PoiFtsEntity
import com.trungkieu.data.features.poi.model.PoiWithCategoriesCrossRef

// Đây là đoạn code sử dụng thư viện Room để khai báo một database cho ứng dụng Android.
// @Database: Annotation của Room, đánh dấu class này là một database.
@Database(
    entities = [
        CategoryEntity::class,
        PoiEntity::class,
        PoiFtsEntity::class,
        PoiWithCategoriesCrossRef::class,
        PoiCommentEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(InstantConverter::class)
abstract class PoiDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun poiDao(): PoiDao
}