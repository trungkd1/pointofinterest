package com.trungkieu.domain.features.poi.interactor

import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.categories.model.PoiComment
import com.trungkieu.data.features.poi.repository.PoiRepository
import com.trungkieu.domain.core.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val repository: PoiRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<GetCommentsUseCase.Params, List<PoiComment>>(dispatcher) {

    override fun operation(params: Params): Flow<List<PoiComment>> {
        return repository.getComments(params.targetId)
    }
    data class Params(val targetId: String)
}