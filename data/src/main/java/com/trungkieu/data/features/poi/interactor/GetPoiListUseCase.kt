package com.trungkieu.domain.features.poi.interactor

import com.trungkieu.data.core.FlowUseCase
import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.categories.model.PoiModel
import com.trungkieu.data.features.categories.model.PoiSortOption
import com.trungkieu.data.features.poi.repository.PoiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPoiListUseCase @Inject constructor(
    private val repository: PoiRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<GetPoiListUseCase.Params?, List<PoiModel>>(dispatcher) {

    override fun operation(params: Params?): Flow<List<PoiModel>> {
        return repository.getPoiList(params?.sortOption)
    }

    data class Params(val sortOption: PoiSortOption)
}