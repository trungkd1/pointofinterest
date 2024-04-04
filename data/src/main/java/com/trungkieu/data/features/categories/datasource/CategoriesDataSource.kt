package com.trungkieu.data.features.categories.datasource

import com.trungkieu.data.features.categories.model.CategoryDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * Categories data source : Thực hiện các thao tác CRUD trong một nguồn dữ liệu cụ thể và kết nối trực tiếp với CategoriesRepository
 *
 *
 * @constructor Create empty Categories data source
 */
interface CategoriesDataSource {

    // Việc lấy dữ liệu từ các nguồn dữ liệu như API mạng hoặc database thường là bất đồng bộ. Flow giúp quản lý việc lấy dữ liệu bất đồng bộ một cách hiệu quả.
    fun getCategories(): Flow<List<CategoryDataModel>>
    fun getCategories(type: String): Flow<List<CategoryDataModel>> = emptyFlow()
    fun getCategories(ids: List<Int>): Flow<List<CategoryDataModel>> = emptyFlow()

    @Throws(java.lang.NullPointerException::class)
    suspend fun getCategory(id: Int): CategoryDataModel {
        return CategoryDataModel.EMPTY
    }

    suspend fun count(): Int = 0
    suspend fun addCategories(models: List<CategoryDataModel>) {}
    suspend fun addCategory(model: CategoryDataModel) {}
    suspend fun deleteCategory(categoryId: Int) {}
    suspend fun updateCategory(model: CategoryDataModel) {}
}