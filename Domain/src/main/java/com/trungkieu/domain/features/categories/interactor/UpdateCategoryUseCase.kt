package com.trungkieu.domain.features.categories.interactor

import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.categories.model.Category
import com.trungkieu.data.features.categories.repository.CategoriesRepository
import com.trungkieu.domain.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Update category use case
 * Được truyền injection từ HiltModel
 * @constructor Create empty Update category use case
 * @property repository : tham số được truyền vào
 * @property dispatcher :
 */
// implement UseCase<UpdateCategoryUseCase.Params, Unit>(dispatcher) dùng để
class UpdateCategoryUseCase @Inject constructor(
    private val repository: CategoriesRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<UpdateCategoryUseCase.Params, Unit>(dispatcher) {

    override suspend fun operation(params: Params) {
        repository.updateCategory(params.category)
    }

    /**
     * Params
     *
     * @constructor Create empty Params
     * @property category
     */
    data class Params(val category: Category)
}