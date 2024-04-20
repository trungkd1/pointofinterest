package com.trungkieu.domain.features.poi.interactor

import com.trungkieu.data.features.poi.repository.PoiRepository
import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.categories.model.PoiModel
import com.trungkieu.domain.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SearchPoiUseCase @Inject constructor(
    private val poiRepository: PoiRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<SearchPoiUseCase.Params, List<PoiModel>>(dispatcher) {

    override suspend fun operation(params: Params): List<PoiModel> =
        poiRepository.searchPoi(params.query)

    data class Params(val query: String)
}