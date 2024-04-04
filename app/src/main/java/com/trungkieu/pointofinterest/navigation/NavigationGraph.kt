package com.trungkieu.pointofinterest.navigation

import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.trungkieu.pointofinterest.features.about.AboutScreen
import com.trungkieu.pointofinterest.features.categories.categoriesGraph
import com.trungkieu.pointofinterest.features.profile.ui.ProfileScreen
import com.trungkieu.pointofinterest.features.home.ui.HomeScreen
import com.trungkieu.pointofinterest.features.main.PoiAppState
import com.trungkieu.pointofinterest.features.poi.create.ui.CreatePoiScreen
import com.trungkieu.pointofinterest.features.poi.view.ui.ViewPoiScreen
import com.trungkieu.pointofinterest.features.search.ui.SearchScreen

@Composable
fun Navigation(appState: PoiAppState, paddingValues: PaddingValues) {
    NavHost(appState.navController, startDestination = Screen.Home.route, Modifier.padding(paddingValues)) {
        composable(Screen.Home.route) {
            HomeScreen(
                // Nếu gán giá trị đầu vào thì phải thêm dầu ngoặc {}
                showSortDialogState = appState.showSortDialog ,
                // onCloseSortDialog là một callback, khi mà đóng dialog sẽ gọi ngược về và lúc đó  appState.showSortDialog sẽ cập nhật giá trị bằng false
                onCloseSortDialog = { -> appState.showSortDialog = false },
                // screen :screen, args là các ballback được trả về : List<Pair<String, Any>>)
                // appState.navigateTo(screen, args) : callback (unit)
                onNavigate = { screen, args -> appState.navigateTo(screen, args) },
                viewModel = hiltViewModel()
            )
        }
        categoriesGraph(appState)
        composable(Screen.Search.route) { SearchScreen(appState) }
        composable(Screen.Profile.route) { ProfileScreen { appState.navigateTo(it) } }
        composable(Screen.About.route) { AboutScreen() }
        composable(
            Screen.CreatePoi.route,
            deepLinks = listOf(
                navDeepLink {
                    action = Intent.ACTION_SEND
                    mimeType = "text/*"
                },
                navDeepLink {
                    action = Intent.ACTION_SEND
                    mimeType = "image/*"
                }
            )
        ) {
            CreatePoiScreen(onCloseScreen = appState::onBackClick)
        }
        composable(
            Screen.ViewPoiDetailed.route,
            arguments = listOf(navArgument(Screen.ViewPoiDetailed.ARG_POI_ID) {
                type = NavType.StringType
                nullable = false
            })
        ) {
            ViewPoiScreen(appState, onCloseScreen = appState::onBackClick)
        }
    }
}