package com.trungkieu.data.features.categories.model

import kotlinx.datetime.Instant

class PoiStatisticsSnapshot(
    val categoriesUsageCount: Map<String, Int>,
    val viewedPoiCount: Int,
    val unViewedPoiCount: Int,
    val poiAdditionsHistory: Map<Instant, Int>
)