package com.trungkieu.data.features.categories.repository

import com.trungkieu.data.core.Local
import com.trungkieu.data.core.Remote
import com.trungkieu.data.features.categories.datasource.CategoriesDataSource
import com.trungkieu.data.features.categories.model.Category
import com.trungkieu.data.features.categories.model.CategoryDataModel
import com.trungkieu.data.features.categories.model.CategoryType
import com.trungkieu.data.features.categories.model.CreateCategoryPayload
import com.trungkieu.data.features.categories.model.toDomain
import com.trungkieu.data.features.categories.model.toDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Categories repository impl :
 *
 * @constructor Create empty Categories repository impl
 * @Remote và @Local giúp framework dependency injection hiểu cách cung cấp các dependency phù hợp cho CategoriesRepositoryImpl.
 * @Remote và @Local là các annotation (đặc tính) được sử dụng để đánh dấu các dependency injection
 * @property remoteDataSource : @Remote Cho biết rằng remoteDataSource được sử dụng để truy cập dữ liệu từ xa, có thể là thông qua API, web service, hoặc các nguồn tương tự.
 * @property localDataSource : @Local Cho biết rằng localDataSource được sử dụng để truy cập dữ liệu cục bộ, có thể là thông qua Room,roalm SharedPreferences, hoặc các thư viện lưu trữ dữ liệu cục bộ khác.
 */
class CategoriesRepositoryImpl @Inject constructor(
    @Remote private val remoteDataSource: CategoriesDataSource,
    @Local private val localDataSource: CategoriesDataSource
) : CategoriesRepository {

    override suspend fun sync() {
        if (localDataSource.count() == 0) {
            remoteDataSource.getCategories().collect {
                localDataSource.addCategories(it)
            }
        }
    }

    override fun getCategories(): Flow<List<Category>> =
        localDataSource.getCategories().mapToDomain()

    override fun getCategories(ids: List<Int>): Flow<List<Category>> =
        localDataSource.getCategories(ids).mapToDomain()

    override fun getCategories(type: CategoryType): Flow<List<Category>> =
        localDataSource.getCategories(type.name).mapToDomain()

    override suspend fun getCategory(id: String): Category =
        localDataSource.getCategory(id.toInt()).toDomain()

    override suspend fun addCategory(payload: CreateCategoryPayload) {
        localDataSource.addCategory(payload.toDataModel())
    }

    override suspend fun updateCategory(category: Category) {
        localDataSource.updateCategory(category.toDataModel())
    }

    override suspend fun deleteCategory(categoryId: String) {
        localDataSource.deleteCategory(categoryId.toInt())
    }

    private fun Flow<List<CategoryDataModel>>.mapToDomain() =
        this.map { list -> list.map { it.toDomain() } }
}