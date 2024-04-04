package com.trungkieu.pointofinterest.features.main

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.trungkieu.pointofinterest.navigation.MenuActionType
import com.trungkieu.pointofinterest.navigation.Screen
import com.trungkieu.pointofinterest.navigation.getMainScreens
import com.trungkieu.pointofinterest.navigation.routeToScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberPoiAppState(
    // Hàm này đảm bảo rằng NavController sẽ không bị tạo lại khi composable được cập nhật.
    navController: NavHostController = rememberNavController(),
    // Đây là phần tạo và lưu trữ thực tế của đối tượng trạng thái snackbarHost
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    // Hàm này đảm bảo rằng CoroutineScope sẽ không bị tạo lại khi composable được cập nhật.
    // có nghĩa là tạo coroutineScope và gán giá trị của rememberCoroutineScope()
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    // Tạo trạng thái cho nội dung tìm kiếm:
    // cách viết trong compose tương tự như khai báo trong kotlin
    // val searchState = MutableState<TextFieldValue> kiểu như khai báo biến
    searchState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) }
): PoiAppState {
    return remember(navController, snackBarHostState, coroutineScope, searchState) {
        PoiAppState(navController, snackBarHostState, coroutineScope, searchState)
    }
}

// dùng stable để tăng khả năng tương thích các phiên bản android
@Stable
class PoiAppState(
    // NavController là một lớp trong Jetpack Compose Navigation giúp quản lý việc điều hướng giữa các màn hình trong ứng dụng Android.
    val navController: NavHostController,

    // SnackbarHostState là một lớp trong Jetpack Compose dùng để quản lý trạng thái của snackbar.
    val snackBarHostState: SnackbarHostState,
    // khai báo một biến có tên coroutineScope với kiểu dữ liệu CoroutineScope.
    val coroutineScope: CoroutineScope,
    // MutableState: Lớp trong Jetpack Compose dùng để lưu trữ trạng thái có thể thay đổi.
    // TextFieldValue Lớp trong Jetpack Compose dùng để lưu trữ trạng thái của một trường nhập liệu văn bản.
    val searchState: MutableState<TextFieldValue>
) {

    // val navBackStackEntry: NavBackStackEntry? là kiểu khai báo biến kiểu NavBackStackEntry có giá trị null hoặc giá trị của NavBackStackEntry
    // @Composable get() : cho biết get() là một hàm composable
    // giá trị của getter sẽ gán cho biến navBackStackEntry
    val navBackStackEntry: NavBackStackEntry?
        @Composable get() = navController.currentBackStackEntryAsState().value

    // tạo biến currentDestination từ đối tượng NavDestination và giá trị từ hàm getter sẽ gán cho currentDestination
    val currentDestination: NavDestination?
        @Composable get() = navBackStackEntry?.destination

    // tạo biến currentScreen từ đối tượng Screen và giá trị từ hàm getter sẽ gán cho currentScreen
    val currentScreen: Screen?
        @Composable get() = routeToScreen(currentDestination?.route)

    // biến isRootScreen có kiểu dữ liệu là boolean
    // và lấy giá trị từ hàm get () của compose, giá trị sẽ là true nếu currentScreen nằm trong Mainscreen hoặc currentscreen là null
    val isRootScreen: Boolean
        @Composable get() = currentScreen in getMainScreens() || currentScreen == null

    // biến isCurrentFullScreen có kiểu dữ liệu là boolean
    // và lấy giá trị từ hàm get () của compose, giá trị sẽ là true khi màn hình fullscreen và ngược lại
    val isCurrentFullScreen: Boolean
        @Composable get() = currentScreen?.isFullScreen == true

    // tạo biến showSearchBarState với giá trị boolean mặc định là false
    var showSearchBarState by mutableStateOf(false)

    // tạo biến showSortDialog với giá trị boolean mặc định là false
    var showSortDialog by mutableStateOf(false)

    // khai báo biến menuItemClickObservers dangj map với key là MenuActionType và value: OnMenuItemListener
    private val menuItemClickObservers = mutableMapOf<MenuActionType, OnMenuItemListener>()

    // hàm đóng màn hình search
    fun closeSearch() {
        showSearchBarState = false
        searchState.value = TextFieldValue("")
        onBackClick()
    }

    // Viết hàm menu đăng ký menu,
    // cách viêt menuItemClickObservers[actionType] = menuItemListener, có nghĩa là map menuItemClickObservers với key actionType được gán giá trị là menuItemListener
    fun registerMenuItemClickObserver(actionType: MenuActionType, menuItemListener: OnMenuItemListener) {
        menuItemClickObservers[actionType] = menuItemListener
    }

    // cách viêt menuItemClickObservers.remove(actionType), có nghĩa là map menuItemClickObservers sẽ remove key với giá trị là actionType
    fun disposeMenuItemObserver(actionType: MenuActionType) {
        menuItemClickObservers.remove(actionType)
    }

    fun navigateToRoot(screen: Screen) {
        // navController diều hướng sang màn hình được nhận từ parameter như screen
        navController.navigate(screen.route) {
            // PopUpto có nghĩa là xoá lịch sử màn hình backStack và set tại màn hình bắt đầu hiển thị
            popUpTo(navController.graph.findStartDestination().id) {
                // mặc dù xoá backstack nhưng nếu set saveState = true sẽ lưu lại trạng thái của màn hình, như vị trí cuộn, dữ liệu hiển thị
                // saveState chỉ lưu trạng thái của màn hình khi nó bị xoá khỏi backstack.
                saveState = true
            }
            // launchSingleTop = false
            // Nếu màn hình B đã tồn tại trong backstack, một phiên bản mới của màn hình B sẽ được tạo và được thêm vào đầu backstack.
            //Backstack sẽ có dạng: A, B, B (mới nhất).
            // khi launchSingleTop = true, nếu bạn điều hướng đến một màn hình đã có trong backstack, hệ thống điều hướng sẽ đưa màn hình đó lên đầu backstack thay vì tạo một phiên bản mới.
            launchSingleTop = true
            // restoreState chỉ khôi phục trạng thái của màn hình nếu nó đã được lưu và restoreState được đặt thành true.
            restoreState = true
        }
    }

    fun navigateTo(screen: Screen, arguments: List<Pair<String, Any>> = emptyList()) {
        val route = if (arguments.isEmpty().not()) { // tương đương !arguments.isEmpty()
            // tạo một đường dẫn đầy đủ đến màn hình đích
            // screen.routePath: Biến này chứa đường dẫn cơ bản của màn hình đích
            screen.routePath + "?" + arguments.joinToString(separator = ",") { it -> "${it.first}=${it.second}" }
        } else {
            screen.route
        }
        navController.navigate(route)
    }

    // hàm thực hiện bakc button
    fun onBackClick() {
        navController.popBackStack()
    }

    // hàm thực hiện chức năng menu khi click từng item
    fun onMenuItemClicked(actionType: MenuActionType) {
        when (actionType) {
            MenuActionType.BACK -> onBackClick()
            MenuActionType.SEARCH -> {
                navigateTo(Screen.Search)
                showSearchBarState = true
            }
            MenuActionType.ADD -> navigateTo(Screen.CategoriesDetailed)
            MenuActionType.SORT -> showSortDialog = true
            else -> menuItemClickObservers[actionType]?.onMenuItemClicked(actionType)
        }
    }
}

interface OnMenuItemListener {
    fun onMenuItemClicked(menuActionType: MenuActionType)
}