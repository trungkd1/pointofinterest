package com.trungkieu.domain.features.poi.interactor

import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.poi.repository.PoiRepository
import com.trungkieu.domain.core.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class  GetUsedCategoriesUseCase @Inject constructor(
    private val repository: PoiRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, List<Int>>(dispatcher) {
    override fun operation(params: Unit): Flow<List<Int>> = repository.getUsedCategories()
}