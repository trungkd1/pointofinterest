package com.trungkieu.pointofinterest.features.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.trungkieu.pointofinterest.R
import com.trungkieu.pointofinterest.navigation.Navigation
import com.trungkieu.pointofinterest.navigation.Screen
import com.trungkieu.pointofinterest.navigation.getMainScreens
import com.trungkieu.pointofinterest.ui.composables.uikit.AppBar
import com.trungkieu.pointofinterest.ui.composables.uikit.BottomBar
import com.trungkieu.pointofinterest.ui.theme.PointOfInterestTheme

@ExperimentalAnimationApi
@Composable
fun PoiMainScreen(
    // appState là biến được tạo ra từ PoiAppState và được gán gíá trị rememberPoiAppState
    // rememberPoiAppState là một hàm được sử dụng trong Jetpack Compose để tạo ra hoặc lấy ra một trạng thái ứng dụng
    // được lấy giá trị từ return remember(navController, snackBarHostState, coroutineScope, searchState) {
    //        PoiAppState(navController, snackBarHostState, coroutineScope, searchState)
    //    } của PoiAppState
    appState: PoiAppState = rememberPoiAppState()
) {
    // là cái giao diện top và bottom với các title
    Scaffold(
        // nền giao diện
        backgroundColor = MaterialTheme.colorScheme.background,
        // thường là button (+) bên góc phải bên dưới của màn hình
        floatingActionButtonPosition = FabPosition.Center,
        // isFloatingActionButtonDocked = true:  sẽ luôn hiển thị ở mép dưới cùng của màn hình,
        isFloatingActionButtonDocked = true,
        // snackbar là dạng notification hiện bên dưới màn hình, kèm theo button hide
        // snackbarHost là một component chứa các snackBar
        // snackbarHost này là thuộc tính của Scaffold
        // Việc sử dụng SnackbarHost bên trong SnackbarHost của Scaffold có thể mang lại lợi ích về khả năng tùy chỉnh, quản lý trạng thái và tích hợp với các thư viện bên thứ ba.
        snackbarHost = { snackbarHostState ->
            SnackbarHost(hostState = appState.snackBarHostState) {
                Snackbar(snackbarData = it, actionColor = MaterialTheme.colorScheme.secondary)
            }
            // ?.let { it -> .method.. }: nếu giá trị không null sẽ gán biến it cho method
            // nếu snackbarHostState.currentSnackbarData != null, thi Snackbar(snackbarData sẽ được gán giá trị it
            // nếu snackbarHostState.currentSnackbarData không null, thì it sẽ là currentSnackbarData.
//            snackbarHostState.currentSnackbarData?.let { Snackbar(snackbarData = it) }

        },
        // là thuộc tinmhs của Scaffold
        floatingActionButton = {
            // kiếm soát khả năng hiển thị của các thành phần con
            AnimatedVisibility(
                // if visible = true, thì layout con sẽ hiện lên với animation
                visible = appState.isCurrentFullScreen.not(),
                //scaleIn() thuốc tính phóng to từ tâm khi vào màn hình con (màn hình kế tiêp)
                enter = scaleIn(),
                //scaleOut() thuốc tính thu nhỏ về tâm từ màn hình con (màn hình kế tiêp)
                exit = scaleOut(),
            ) {
                FloatingActionButton(
                    // set backgraound của floatButton
                    // khi click vào float button sẽ di chuyển đến màn hình CreatePoi
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    onClick = {
                        appState.navigateTo(Screen.CreatePoi)
                    }) {
                    // icon của float button
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = MaterialTheme.colorScheme.onPrimary)
                }
            }
        },
        topBar = { AppBar(title = stringResource(id = R.string.app_name), appState) },
        bottomBar = {
            if (appState.isCurrentFullScreen.not()) {
                BottomBar(appState, items = getMainScreens())
            }
        }) { paddingValues -> Navigation(appState = appState, paddingValues = paddingValues) }
}

@Preview
@Composable
@ExperimentalAnimationApi
fun RootScreenPreview() {
    PointOfInterestTheme() { PoiMainScreen() }
}