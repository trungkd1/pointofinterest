package com.trungkieu.pointofinterest.navigation

import androidx.annotation.DrawableRes
import com.trungkieu.pointofinterest.R

sealed class MenuItem(@DrawableRes val icon: Int, val action: MenuActionType) {
    object Back : MenuItem(R.drawable.ic_back, MenuActionType.BACK)
    object Search : MenuItem(R.drawable.ic_search, MenuActionType.SEARCH)
    object SortBy : MenuItem(R.drawable.ic_sort, MenuActionType.SORT)
    object Add : MenuItem(R.drawable.ic_add, MenuActionType.ADD)
    object Delete : MenuItem(R.drawable.ic_delete_forever, MenuActionType.DELETE)
}

enum class MenuActionType {
    BACK, SEARCH, ADD, SORT, DELETE
}
