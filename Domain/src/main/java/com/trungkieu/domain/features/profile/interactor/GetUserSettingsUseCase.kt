package com.trungkieu.domain.features.profile.interactor

import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.profile.model.UserSettings
import com.trungkieu.data.features.profile.repository.ProfileRepository
import com.trungkieu.domain.core.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Đây là class một usecase trong app, một usecase thường được định nghĩa là một chức năng
// @Inject constructor : có nghĩa GetUserSettingsUseCase được tiêm vào từ class  ViewModel
class GetUserSettingsUseCase @Inject constructor(
    private val repository: ProfileRepository,
    // Dagger Hilt sẽ tự động tiêm một instance của Dispatchers.IO vào biến dispatcher.
    // CoroutineDispatcher là một một điều phối các tác vụ coroutine
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, UserSettings>(dispatcher) {
    // hàm operation nhận tham số đầu vào là Unit và trả về một flow của đối tượng UserSettings
    override fun operation(params: Unit): Flow<UserSettings> =
        repository.getUserSetting()
}