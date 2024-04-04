package com.trungkieu.domain.features.poi.interactor

import com.trungkieu.data.core.UseCase
import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.categories.model.PoiCommentPayload
import com.trungkieu.data.features.poi.repository.PoiRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(
    private val repository: PoiRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<AddCommentUseCase.Params, Unit>(dispatcher) {

    override suspend fun operation(params: Params) {
        repository.addComment(params.targetId, params.payload)
    }

    data class Params(val targetId: String, val payload: PoiCommentPayload)
}