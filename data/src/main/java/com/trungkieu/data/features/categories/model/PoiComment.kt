package com.trungkieu.data.features.categories.model

import kotlinx.datetime.Instant

data class PoiComment(
    val id: String,
    val message: String,
    val commentDate: Instant
)