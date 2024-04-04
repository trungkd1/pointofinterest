package com.trungkieu.domain.features.categories.interactor

import com.trungkieu.data.core.FlowUseCase
import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.categories.model.Category
import com.trungkieu.data.features.categories.repository.CategoriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoriesRepository, // tham số repository sẽ được dagger inject và interface CategoriesRepository, nơi mà nó được @blinds (thực thi CategoriesRepository)  tại class RepoModule
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, List<Category>>(dispatcher) {
    override fun operation(params: Unit): Flow<List<Category>> = repository.getCategories()
}