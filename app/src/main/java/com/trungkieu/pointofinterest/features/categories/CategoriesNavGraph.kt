package com.trungkieu.pointofinterest.features.categories

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.trungkieu.pointofinterest.features.categories.ui.CategoriesDetailedScreen
import com.trungkieu.pointofinterest.features.categories.ui.CategoriesScreen
import com.trungkieu.pointofinterest.features.categories.vm.CategoriesViewModel
import com.trungkieu.pointofinterest.features.main.PoiAppState
import com.trungkieu.pointofinterest.navigation.Screen
import com.trungkieu.pointofinterest.navigation.Screen.CategoriesDetailed.ARG_CATEGORY_ID

fun NavGraphBuilder.categoriesGraph(appState: PoiAppState) {
    navigation(startDestination = Screen.CategoriesList.route, route = Screen.Categories.route) {
        composable(Screen.CategoriesList.route) {
            val viewModel = hiltViewModel<CategoriesViewModel>()
            CategoriesScreen(appState.snackBarHostState, appState.coroutineScope, viewModel, appState::navigateTo)
        }
        composable(
            Screen.CategoriesDetailed.route,
            arguments = listOf(navArgument(ARG_CATEGORY_ID) {
                type = NavType.StringType
                nullable = true
            })
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                appState.navController.getBackStackEntry(Screen.CategoriesList.route)
            }
            val parentViewModel = hiltViewModel<CategoriesViewModel>(parentEntry)
            val categoryId = backStackEntry.arguments?.getString(ARG_CATEGORY_ID)

            CategoriesDetailedScreen(categoryId = categoryId, parentViewModel, appState::onBackClick)
        }
    }
}