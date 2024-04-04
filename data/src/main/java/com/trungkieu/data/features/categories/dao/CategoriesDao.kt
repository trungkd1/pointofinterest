package com.trungkieu.data.features.categories.dao

import androidx.room.*
import com.trungkieu.data.features.categories.model.CategoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Categories dao : sẽ được gọi từ class CategoriesLocalDataSource và được dagger tạo instance
 * interface này la nơii truy cập các câu lệnh SQL
 *
 * @constructor Create empty Categories dao
 */
@Dao // @Dao là một annotation được sử dụng trong Dagger Hilt để tạo ra các class Data Access Object (DAO).
// DAO là một lớp giúp bạn truy cập dữ liệu từ một nguồn dữ liệu cụ thể, chẳng hạn như SQLite database hoặc API mạng
interface CategoriesDao {

    @Query(value = "SELECT * FROM table_categories")
    fun getCategories(): Flow<List<CategoryEntity>>

    @Query(value = "SELECT * FROM table_categories WHERE id IN (:idList)")
    fun getCategories(idList: List<Int>): Flow<List<CategoryEntity>>

    @Query(value = "SELECT * FROM table_categories WHERE type = :type")
    fun getCategories(type: String): Flow<List<CategoryEntity>>

    @Query(value = "SELECT * FROM table_categories WHERE id = :categoryId")
    suspend fun getCategory(categoryId: Int): CategoryEntity

    @Query("SELECT COUNT(*) FROM table_categories")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categoryEntity: List<CategoryEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryEntity: CategoryEntity): Long

    @Update
    suspend fun updateCategory(categoryEntity: CategoryEntity)

    @Query(value = "DELETE FROM table_categories WHERE id = :categoryId")
    suspend fun deleteCategory(categoryId: Int)

    @Query(value = "DELETE FROM table_categories")
    suspend fun deleteAll()
}