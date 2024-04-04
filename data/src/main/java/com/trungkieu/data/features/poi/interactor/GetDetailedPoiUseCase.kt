package com.trungkieu.domain.features.poi.interactor

import com.trungkieu.data.core.UseCase
import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.categories.model.PoiModel
import com.trungkieu.data.features.poi.repository.PoiRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetDetailedPoiUseCase @Inject constructor(
    private val repository: PoiRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<GetDetailedPoiUseCase.Params, PoiModel>(dispatcher) {

    override suspend fun operation(params: Params): PoiModel {
        return repository.getDetailedPoi(params.id)
    }

    data class Params(val id: String)
}