package com.trungkieu.domain.features.categories.interactor

import com.trungkieu.data.core.UseCase
import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.categories.repository.CategoriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SyncCategoriesUseCase @Inject constructor(
    private val repository: CategoriesRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<Unit, Unit>(dispatcher) {

    override suspend fun operation(params: Unit) {
        repository.sync()
    }
}