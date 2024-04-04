package com.trungkieu.domain.features.profile.interactor

import com.trungkieu.data.core.FlowUseCase
import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.profile.model.Profile
import com.trungkieu.data.features.profile.repository.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, Profile>(dispatcher) {

    override fun operation(params: Unit): Flow<Profile> =
        repository.getUserProfile()
            .combine(repository.getUserSetting()) { profile, settings ->
                Profile(profile, settings)
            }
}