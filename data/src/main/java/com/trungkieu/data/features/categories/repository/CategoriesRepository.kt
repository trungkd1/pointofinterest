package com.trungkieu.data.features.categories.repository

import com.trungkieu.data.features.categories.model.Category
import com.trungkieu.data.features.categories.model.CategoryType
import com.trungkieu.data.features.categories.model.CreateCategoryPayload
import kotlinx.coroutines.flow.Flow

/**
 * Categories repository : thì truyền vào object Category khác với CategoriesDataSource truyền vào CategoryDataModel
 *
 * @constructor Create empty Categories repository
 */
interface CategoriesRepository {

    suspend fun sync()

    fun getCategories(): Flow<List<Category>>

    fun getCategories(ids: List<Int>): Flow<List<Category>>

    fun getCategories(type: CategoryType): Flow<List<Category>>

    suspend fun getCategory(id: String): Category

    suspend fun addCategory(payload: CreateCategoryPayload)

    suspend fun updateCategory(category: Category)

    suspend fun deleteCategory(categoryId: String)
}