package com.trungkieu.domain.features.categories.interactor

import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.categories.model.Category
import com.trungkieu.data.features.categories.repository.CategoriesRepository
import com.trungkieu.domain.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val repository: CategoriesRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<GetCategoryUseCase.Params, Category>(dispatcher) {

    override suspend fun operation(params: Params): Category = repository.getCategory(params.categoryId)

    data class Params(val categoryId: String)
}