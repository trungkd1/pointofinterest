package com.trungkieu.domain.features.categories.interactor

import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.categories.model.Category
import com.trungkieu.data.features.categories.repository.CategoriesRepository
import com.trungkieu.domain.core.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesByIdsUseCase @Inject constructor(
    private val repository: CategoriesRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<GetCategoriesByIdsUseCase.Params, List<Category>>(dispatcher) {

    override fun operation(params: Params): Flow<List<Category>> = repository.getCategories(params.ids)

    data class Params(val ids: List<Int>)
}