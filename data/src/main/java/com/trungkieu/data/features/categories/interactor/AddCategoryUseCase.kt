package com.trungkieu.domain.features.categories.interactor

import com.trungkieu.data.core.UseCase
import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.categories.model.CreateCategoryPayload
import com.trungkieu.data.features.categories.repository.CategoriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(
    private val repository: CategoriesRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<AddCategoryUseCase.Params, Unit>(dispatcher) {

    override suspend fun operation(params: Params) {
        repository.addCategory(params.toPayload())
    }

    private fun Params.toPayload() = CreateCategoryPayload(title, color)

    data class Params(
        val title: String,
        val color: Int
    )
}