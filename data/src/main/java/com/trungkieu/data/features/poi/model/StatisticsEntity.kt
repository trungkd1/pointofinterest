package com.trungkieu.data.features.poi.model

data class UsedCategoriesStatisticsEntity(
    val categoryId: Int,
    val count: Int
)

data class PoiHistoryEntity(
    val date: String,
    val count: Int
)