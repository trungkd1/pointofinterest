package com.trungkieu.domain.features.profile.interactor

import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.profile.model.UserProfile
import com.trungkieu.data.features.profile.repository.ProfileRepository
import com.trungkieu.domain.core.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SetUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<SetUserProfileUseCase.Params, Unit>(dispatcher) {

    override suspend fun operation(params: Params) {
        repository.setUserProfile(params.userProfile)
    }

    data class Params(val userProfile: UserProfile)
}