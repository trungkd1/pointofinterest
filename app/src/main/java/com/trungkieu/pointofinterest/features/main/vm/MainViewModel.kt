package com.trungkieu.pointofinterest.features.main.vm

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trungkieu.data.features.profile.model.UserSettings
import com.trungkieu.domain.features.categories.interactor.SyncCategoriesUseCase
import com.trungkieu.domain.features.profile.interactor.GetUserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel là một lớp giúp lưu trữ dữ liệu và logic cho một màn hình.
//Bạn có thể sử dụng ViewModel để truyền dữ liệu giữa các màn hình mà không cần sử dụng Intent hoặc Bundle.
// MainViewModel được quản lý bởi Hilt
@HiltViewModel
class MainViewModel @Inject constructor(
    //getUserSettingsUseCase sẽ được inject từ mainViewModel thay vì GetUserSettingsUseCase getUserSettingsUseCase = new GetUserSettingsUseCase() sẽ phải tạo một đối tượng mỗi khi gọi hàm bên trong
    getUserSettingsUseCase: GetUserSettingsUseCase,
    // Tạo một đối tượng syncCategoriesUseCase được quản lý bởi Hilt
    private val syncCategoriesUseCase: SyncCategoriesUseCase
) : ViewModel(), DefaultLifecycleObserver {

    private val _syncState = MutableStateFlow<SyncState>(SyncState.Loading)
    val syncState = _syncState.asStateFlow()
    // Trong Kotlin, bạn có thể gọi một hàm mà không cần chỉ định tên hàm nếu hàm đó chỉ có một tham số.
    //Ví dụ: foo(10) tương đương với foo.invoke(10).
    val mainScreenState = getUserSettingsUseCase.operation(Unit)
        // .map là một toán tử được sử dụng để chuyển đổi dữ liệu bên trong một luồng (flow) trong Kotlin.
        .map { MainScreenState.Result(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MainScreenState.Loading
        )

    // được tạo ra từ việc implement DefaultLifecycleObserver để theo dõi vòng đời của Acivity hoặc Fragment từ bước OnCreate
    override fun onCreate(owner: LifecycleOwner) {
        viewModelScope.launch {
            delay(500)
            syncCategoriesUseCase(Unit)
            _syncState.value = SyncState.Success
        }
    }
}

// sealed chính là cách viết dạng final
//  không thể kế thừa từ class sealed.
// không thể override các phương thức của class sealed.
// Sealed class là một công cụ mạnh mẽ để giúp bạn quản lý các trạng thái có thể xảy ra trong code của bạn.
sealed class SyncState {
    object Loading : SyncState()
    object Success : SyncState()
}

sealed class MainScreenState {
    object Loading : MainScreenState()
    data class Result(val userSettings: UserSettings) : MainScreenState()
}
