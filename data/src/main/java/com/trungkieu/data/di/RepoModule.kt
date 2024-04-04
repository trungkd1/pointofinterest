package com.trungkieu.data.di

import com.trungkieu.data.features.categories.repository.CategoriesRepository
import com.trungkieu.data.features.categories.repository.CategoriesRepositoryImpl
import com.trungkieu.data.features.poi.repository.PoiRepository
import com.trungkieu.data.features.poi.repository.PoiRepositoryImpl
import com.trungkieu.data.features.profile.repository.ProfileRepository
import com.trungkieu.data.features.profile.repository.ProfileRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Repo module : khai báo một interface nhưng chỉ định nghĩa các phương thức và hằng số.
 *
 * @constructor Create empty Repo module : Interface RepoModule không cần được gọi trực tiếp khi sử dụng.
 * Dagger sẽ tự động tìm kiếm và sử dụng các module được anotate với @Module trong quá trình khởi tạo application.
 * Dagger sẽ tìm thấy RepoModule và đọc các ràng buộc được định nghĩa trong class đó.
 * Dagger sẽ biết rằng interface CategoriesRepository được ràng buộc với concrete implementation class CategoriesRepositoryImpl.
 */
@Module // đây là một anotation của dagger dùng để liên kết các dữ liệu
@InstallIn(SingletonComponent::class) //  để đảm bảo rằng chỉ có một instance duy nhất được tạo ra. và RepoModule được sử dụng suốt vòng đời ứng dụng
interface RepoModule {

    /**
     * Bind categories repo
     *
     * @param impl
     * @return
     */
    @Binds // @Binds được sử dụng để ràng buộc một interface với một class cụ thể..
    // Không trực tiếp tạo instance, chỉ ràng buộc interface với implementation. Dagger sẽ tìm cách tạo instance theo cách khác (thường là thông qua constructor injection).
    @Singleton //  Annotation này đảm bảo rằng Dagger chỉ tạo một instance duy nhất của kiểu được ràng buộc và sử dụng lại nó trong suốt ứng dụng.
    fun bindCategoriesRepo(impl: CategoriesRepositoryImpl) : CategoriesRepository // Trả về kiểu interface


    /**
     * Bind profile repo
     *
     * @param impl
     * @return
     */
    @Binds // @Binds được sử dụng để ràng buộc một interface với một class cụ thể..
    @Singleton
    fun bindProfileRepo(impl: ProfileRepositoryImpl): ProfileRepository

    /**
     * Bind poi repo
     *
     * @param impl
     * @return
     */
    @Binds
    @Singleton
    fun bindPoiRepo(impl: PoiRepositoryImpl): PoiRepository
}