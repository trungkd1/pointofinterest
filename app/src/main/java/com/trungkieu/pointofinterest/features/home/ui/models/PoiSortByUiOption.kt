package com.trungkieu.pointofinterest.features.home.ui.models

import com.trungkieu.data.features.categories.model.PoiSortOption
import com.trungkieu.pointofinterest.R

enum class PoiSortByUiOption {
    DATE, SEVERITY, TITLE
}

fun PoiSortByUiOption.toTitle() = when (this) {
    PoiSortByUiOption.SEVERITY -> R.string.title_sort_by_severity
    PoiSortByUiOption.DATE -> R.string.title_sort_by_date
    PoiSortByUiOption.TITLE -> R.string.title_sort_by_title
}

fun PoiSortByUiOption.toSubTitle() = when (this) {
    PoiSortByUiOption.SEVERITY -> R.string.subtitle_sort_by_severity
    PoiSortByUiOption.DATE -> R.string.subtitle_sort_by_date
    PoiSortByUiOption.TITLE -> R.string.subtitle_sort_by_title
}

fun PoiSortByUiOption.toDomain() = when (this) {
    PoiSortByUiOption.SEVERITY -> PoiSortOption.SEVERITY
    PoiSortByUiOption.TITLE -> PoiSortOption.TITLE
    else -> PoiSortOption.DATE
}