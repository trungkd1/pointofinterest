package com.trungkieu.domain.features.poi.interactor

import com.trungkieu.data.core.UseCase
import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.poi.repository.PoiRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DeleteCommentUseCase @Inject constructor(
    private val poiRepository: PoiRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<DeleteCommentUseCase.Params, Unit>(dispatcher) {

    override suspend fun operation(params: Params) {
        poiRepository.deleteComment(params.id)
    }

    data class Params(val id: String)
}