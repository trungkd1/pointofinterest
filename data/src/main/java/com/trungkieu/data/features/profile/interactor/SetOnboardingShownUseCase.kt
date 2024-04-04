package com.trungkieu.domain.features.profile.interactor

import com.trungkieu.data.core.UseCase
import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.profile.repository.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SetOnboardingShownUseCase @Inject constructor(
    private val repository: ProfileRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<Unit, Unit>(dispatcher) {

    override suspend fun operation(params: Unit) {
        repository.setShowOnBoarding(false)
    }
}