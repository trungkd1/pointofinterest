package com.trungkieu.data.di

import com.trungkieu.data.core.Local
import com.trungkieu.data.core.Remote
import com.trungkieu.data.features.categories.datasource.CategoriesDataSource
import com.trungkieu.data.features.categories.datasource.CategoriesFakeRemoteDataSource
import com.trungkieu.data.features.categories.datasource.CategoriesLocalDataSource
import com.trungkieu.data.features.poi.datasource.*
import com.trungkieu.data.features.profile.datasource.ProfileDataSource
import com.trungkieu.data.features.profile.datasource.ProfileLocalDataSource
import com.trungkieu.data.features.poi.datasource.ImageDataSource
import com.trungkieu.data.features.poi.datasource.LocalImageDataSource
import com.trungkieu.data.features.poi.datasource.PoiDataSource
import com.trungkieu.data.features.poi.datasource.PoiLocalDataSource
import com.trungkieu.data.features.poi.datasource.WizardDataSource
import com.trungkieu.data.features.poi.datasource.WizardRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Data source module
 *
 * @constructor Create empty Data source module
 */
@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    /**
     * Bind category local data source
     *
     * @param dataSource có nghĩa là truyền param này vào class CategoriesRepositoryImpl
     * CategoriesRepositoryImpl(@Local private val localDataSource: CategoriesDataSource = dataSource)
     * @return = CategoriesDataSource
     */
    @Binds
    @Local
    fun bindCategoryLocalDataSource(dataSource: CategoriesLocalDataSource): CategoriesDataSource

    /**
     * Bind category remote data source
     *
     * @param dataSource có nghĩa là truyền param này vào class CategoriesRepositoryImpl
     *      CategoriesRepositoryImpl(@Remote private val remoteDataSource: CategoriesDataSource = dataSource)
     * @return = CategoriesDataSource
     */
    @Binds
    @Remote
    fun bindCategoryRemoteDataSource(dataSource: CategoriesFakeRemoteDataSource): CategoriesDataSource

    /**
     * Bind profile local data source
     *
     * @param dataSource có nghĩa là truyền param này vào class ProfileRepositoryImpl
     *          ProfileRepositoryImpl(@Local private val dataSource: ProfileDataSource = dataSource)
     * @return = ProfileDataSource
     */
    @Binds
    @Local
    fun bindProfileLocalDataSource(dataSource: ProfileLocalDataSource): ProfileDataSource

    /**
     * Bind poi local data source
     *
     * @param dataSource có nghĩa là truyền param này vào class PoiRepositoryImpl
     *      PoiRepositoryImpl(@Local private val localDataSource: PoiDataSource = dataSource)
     * @return = PoiDataSource
     */
    @Binds
    @Local
    fun bindPoiLocalDataSource(dataSource: PoiLocalDataSource): PoiDataSource

    /**
     * Bind local image data source
     *
     * @param dataSource có nghĩa là truyền param này vào class PoiRepositoryImpl
     *      PoiRepositoryImpl(@Local private val imageDataSource: ImageDataSource = dataSource)
     * @return = ImageDataSource
     */
    @Binds
    @Local
    fun bindLocalImageDataSource(dataSource: LocalImageDataSource): ImageDataSource

    /**
     * Bind wizard remote data source
     *
     * @param dataSource có nghĩa là truyền param này vào class PoiRepositoryImpl
     *       PoiRepositoryImpl(@Remote private val wizardRemoteDataSource: WizardRemoteDataSource = dataSource)
     * @return = WizardDataSource
     */
    @Binds
    @Remote
    fun bindWizardRemoteDataSource(dataSource: WizardRemoteDataSource): WizardDataSource
}