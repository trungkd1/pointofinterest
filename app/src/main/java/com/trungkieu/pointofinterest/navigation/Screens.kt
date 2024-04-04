package com.trungkieu.pointofinterest.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.trungkieu.pointofinterest.R

sealed class Screen(
    val route: String,
    @StringRes val name: Int,
    @DrawableRes val icon: Int? = null,
    val isFullScreen: Boolean = false,
    val menuItems: List<MenuItem> = emptyList(),
    val routePath: String = "",
) {
    object Home : Screen(
        route = "screen_home",
        R.string.screen_home,
        R.drawable.ic_home,
        menuItems = arrayListOf(MenuItem.Search, MenuItem.SortBy)
    )

    object Search : Screen(
        route = "screen_search",
        R.string.screen_home,
        isFullScreen = true
    )

    object Profile : Screen(route = "screen_profile", R.string.screen_profile, R.drawable.ic_profile)

    object About : Screen(route = "screen_about", R.string.screen_about, isFullScreen = true)

    object Categories : Screen(
        route = "screen_categories",
        R.string.screen_categories,
        isFullScreen = true,
    )

    object CategoriesList : Screen(
        route = "screen_categories_list",
        R.string.screen_categories,
        R.drawable.ic_category,
        isFullScreen = true,
        menuItems = arrayListOf(MenuItem.Add)
    )

    object CategoriesDetailed : Screen(
        route = "screen_category_detailed?categoryId={categoryId}",
        routePath = "screen_category_detailed",
        name = R.string.screen_categories,
        isFullScreen = true,
    ) {
        const val ARG_CATEGORY_ID = "categoryId"
    }

    object CreatePoi : Screen(route = "screen_create_poi", R.string.screen_poi, isFullScreen = true)

    object ViewPoiDetailed : Screen(
        route = "screen_poi_detailed?poiId={poiId}",
        routePath = "screen_poi_detailed",
        name = R.string.screen_poi,
        isFullScreen = true,
        menuItems = arrayListOf(MenuItem.Delete)
    ) {
        const val ARG_POI_ID = "poiId"
    }

    companion object {
        val all = arrayListOf(
            Home,
            Search,
            Categories,
            Profile,
            CreatePoi,
            ViewPoiDetailed,
            CategoriesList,
            CategoriesDetailed,
            About
        )
    }
}

fun getMainScreens() = arrayListOf(Screen.Home, Screen.Profile)

fun routeToScreen(route: String?): Screen? = Screen.all.find { it.route == route }