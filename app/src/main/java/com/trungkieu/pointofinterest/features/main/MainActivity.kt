package com.trungkieu.pointofinterest.features.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.trungkieu.pointofinterest.features.main.vm.MainScreenState
import com.trungkieu.pointofinterest.features.main.vm.SyncState
import com.trungkieu.pointofinterest.features.main.vm.MainViewModel
import com.trungkieu.pointofinterest.ui.theme.PointOfInterestTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private var navHostController: NavHostController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // splashScreen thường là màn hình giowis thiệu trước khi vào app
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // Tạo một biến có tên mainState với kiểu dữ liệu là MainScreenState.
        //Gán giá trị khởi tạo cho mainState là MainScreenState.Loading
        var mainState: MainScreenState by mutableStateOf(MainScreenState.Loading)
        // Update the uiState
        // lifecycleScope là một coroutine thực hiện việc bất đồng bộ
        // Khởi chạy một coroutine mới trong phạm vi vòng đời của lifecycleScope.
        // Coroutine này sẽ bị hủy tự động khi trạng thái lifecycle của Activity/Fragment thay đổi (ví dụ, khi Activity/Fragment bị destroyed).
        lifecycleScope.launch {
            // Code này đảm bảo rằng trạng thái UI của màn hình chính được cập nhật chỉ khi Activity/Fragment đang hiển thị (trạng thái STARTED).
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Truy cập vào Flow chứa trạng thái UI của màn hình chính, được cung cấp bởi mainViewModel.
                // Đoạn code này sử dụng Flow để cập nhật trạng thái mainState trong MainViewModel.
                mainViewModel.mainScreenState
                    .onEach { // Mỗi lần Flow phát ra một giá trị mới (trạng thái UI mới), khối code bên trong onEach sẽ được thực thi.
                        mainState = it // Gán giá trị mới được phát ra bởi Flow (trạng thái UI mới) vào biến mainState.
                    }
                    .collect() // Khởi chạy việc thu thập các giá trị từ Flow.
            }
        }

        // splashScreen sẽ vẫn giữ trạng thái loading cho đén khi mainState thay đổi trạng thái
        splashScreen.setKeepOnScreenCondition {
            // syncStateIsLoading sẽ kiểm tra SyncState có đang ở trạng thái loading hay không
            // mainStateIsLoading sẽ kiểm tra MainScreenState có đang ở trạng thái loading hay không
            val syncStateIsLoading = mainViewModel.syncState.value == SyncState.Loading
            val mainStateIsLoading = mainState == MainScreenState.Loading

            // Nếu cả 2 cùng loading thì trả về true
            return@setKeepOnScreenCondition syncStateIsLoading && mainStateIsLoading
        }

        // dùng đề tracking lifecycle của activity bên CLassViewModel khi implement DefaultLifecycleObserver
        lifecycle.addObserver(mainViewModel)

        setContent {
            // gọi hàm PointOfInterestTheme từ file theme.kt
            PointOfInterestTheme(
                useSystemTheme = mainState.userSettings()?.isUseSystemTheme ?: true,
                darkTheme = mainState.userSettings()?.isDarkMode ?: true
            ) {
                // tạo navHostController để điều di chuyển giữa các màn hình
                navHostController = rememberNavController()
                // Tạo appState của ứng dụng và truyền biến này vào PoiMainScreen
                val appState = rememberPoiAppState(navController = requireNotNull(navHostController))
                PoiMainScreen(appState)
            }
        }
    }

    //Khi ứng dụng được mở từ một deep link (liên kết đặc biệt), phương thức onNewIntent sẽ được gọi với intent chứa thông tin deep link.
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // HandleDeepLink là một phương thức được sử dụng để xử lý thông tin deep link trong ứng dụng Android
        // Deep link là một liên kết đặc biệt được sử dụng để mở ứng dụng và điều hướng đến một màn hình cụ thể
        navHostController?.handleDeepLink(intent)
    }

    // Biểu thức (this as? MainScreenState.Result) kiểm tra xem this có thể được cast thành MainScreenState.Result hay không.
    private fun MainScreenState.userSettings() = if (this is MainScreenState.Result) userSettings else null
}