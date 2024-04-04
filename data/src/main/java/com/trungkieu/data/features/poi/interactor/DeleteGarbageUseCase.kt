package com.trungkieu.domain.features.poi.interactor

import com.trungkieu.data.core.UseCase
import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.poi.repository.PoiRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DeleteGarbageUseCase @Inject constructor(
    private val repository: PoiRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<Unit, Int>(dispatcher) {
    override suspend fun operation(params: Unit): Int {
        return repository.deleteGarbage()
    }
}