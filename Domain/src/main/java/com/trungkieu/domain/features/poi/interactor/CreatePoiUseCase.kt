package com.trungkieu.domain.features.poi.interactor

import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.categories.model.PoiCreationPayload
import com.trungkieu.data.features.poi.repository.PoiRepository
import com.trungkieu.domain.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CreatePoiUseCase @Inject constructor(
    private val repository: PoiRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<CreatePoiUseCase.Params, Unit>(dispatcher) {

    override suspend fun operation(params: Params) {
        repository.createPoi(params.payload)
    }

    data class Params(val payload: PoiCreationPayload)
}